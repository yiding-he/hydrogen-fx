package com.hyd.fx.app;

import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * 对 {@link Preferences} 的简单封装(WIP)
 */
public class AppPreferences extends AbstractPreferences {

    public AppPreferences(String filePath) {
        super(null, "");
    }

    private AppPreferences(AbstractPreferences parent, String name) {
        super(parent, name);
    }

    @Override
    protected void putSpi(String key, String value) {

    }

    @Override
    protected String getSpi(String key) {
        return null;
    }

    @Override
    protected void removeSpi(String key) {

    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {

    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        return new String[0];
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        return new String[0];
    }

    @Override
    protected AbstractPreferences childSpi(String name) {
        return new AppPreferences(this, name);
    }

    @Override
    protected void syncSpi() throws BackingStoreException {

    }

    @Override
    protected void flushSpi() throws BackingStoreException {

    }
}
