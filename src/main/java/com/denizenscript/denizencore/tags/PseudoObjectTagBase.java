package com.denizenscript.denizencore.tags;

import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;

public abstract class PseudoObjectTagBase<T extends PseudoObjectTagBase<T>> extends ObjectTagBase<T> implements ObjectTag {
    public PseudoObjectTagBase(Class<T> thisClass) {
        super(thisClass);
    }

    @Override
    public String getObjectIdentifier() {
        return null;
    }

    @Override
    public T getDefault(TagContext context) {
        return (T) this;
    }

    @Override
    public T valueOf(String input, TagContext context) {
        return null;
    }

    @Override
    public boolean matches(String input) {
        return false;
    }

    @Override
    public T getForObject(ObjectTag objectTag, TagContext context) {
        return null;
    }

    @Override
    public boolean shouldBeType(Class<? extends ObjectTag> type) {
        return false;
    }

    @Override
    public String getPrefix() {
        return identify();
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String identify() {
        return "(Base-Object)";
    }

    @Override
    public String toString() {
        return identify();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public ObjectTag setPrefix(String prefix) {
        return this;
    }

    @Override
    public ObjectTag getNextObjectTypeDown() {
        return new ElementTag.FailedObjectTag();
    }

    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {
        return tagProcessor.getObjectAttribute((T) this, attribute);
    }
}
