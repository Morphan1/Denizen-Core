package net.aufdemrand.denizencore.scripts.commands.queue;

import net.aufdemrand.denizencore.exceptions.InvalidArgumentsException;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.objects.aH;
import net.aufdemrand.denizencore.scripts.ScriptEntry;
import net.aufdemrand.denizencore.scripts.commands.AbstractCommand;
import net.aufdemrand.denizencore.utilities.debugging.dB;

import java.util.List;

public class GotoCommand extends AbstractCommand {

    // <--[command]
    // @Name Goto
    // @Syntax goto [<name>]
    // @Required 1
    // @Short Jump forward to a location marked by <@link command mark>.
    // @Group queue
    //
    // @Description
    // Jumps forward to a marked location in the script.
    // For example:
    // <code>
    // - goto potato
    // - narrate "This will never show"
    // - mark potato
    // </code>
    //
    // @Tags
    //
    // None
    //
    // @Usage
    // Use to jump forward to a location.
    // - goto potato
    // -->

    @Override
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {

        // Interpret arguments
        for (aH.Argument arg : aH.interpretArguments(scriptEntry.aHArgs)) {

            if (!scriptEntry.hasObject("m_name")) {
                scriptEntry.addObject("m_name", arg.asElement());
            }

            else {
                arg.reportUnhandled();
            }
        }

        // Check for required information
        if (!scriptEntry.hasObject("m_name")) {
            throw new InvalidArgumentsException("Must have a mark name!");
        }

    }


    @Override
    public void execute(ScriptEntry scriptEntry) {

        // Fetch required objects
        Element mName = scriptEntry.getElement("m_name");

        // Debug the execution
        if (scriptEntry.dbCallShouldDebug()) {
            dB.report(scriptEntry, getName(), mName.debug());
        }

        // Jump forth
        boolean hasmark = false;
        for (int i = 0; i < scriptEntry.getResidingQueue().getQueueSize(); i++) {
            ScriptEntry entry = scriptEntry.getResidingQueue().getEntry(i);
            List<String> args = entry.getOriginalArguments();
            if (entry.getCommandName().equalsIgnoreCase("mark") && args.size() > 0 && args.get(0).equalsIgnoreCase(mName.asString())) {
                hasmark = true;
                break;
            }
        }
        if (hasmark) {
            while (scriptEntry.getResidingQueue().getQueueSize() > 0) {
                ScriptEntry entry = scriptEntry.getResidingQueue().getEntry(0);
                List<String> args = entry.getOriginalArguments();
                if (entry.getCommandName().equalsIgnoreCase("mark") && args.size() > 0 && args.get(0).equalsIgnoreCase(mName.asString())) {
                    break;
                }
                scriptEntry.getResidingQueue().removeEntry(0);
            }
        }
        else {
            dB.echoError(scriptEntry.getResidingQueue(), "Cannot go to that location - doesn't seem to exist!");
        }
    }
}