package com.hyd.fx.attachable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 可附加到其他对象的对象。通过 Attachable，可以将一些操作附加到现有的 Node 对象上，而无须从这些对象继承。
 *
 * @param <T>
 */
public abstract class Attachable<T> {

    private static final WeakHashMap<Object,
            Map<Class<? extends Attachable>, Attachable>> ATTACHABLE_MAP = new WeakHashMap<>();

    private static synchronized void register(Object attached, Attachable attachable) {
        ATTACHABLE_MAP.computeIfAbsent(attached, k -> new HashMap<>()).put(attachable.getClass(), attachable);
    }

    static synchronized void unregister(Object attached, Class<? extends Attachable> type) {
        if (attached != null) {
            if (ATTACHABLE_MAP.containsKey(attached)) {
                ATTACHABLE_MAP.get(attached).remove(type);
            }
        }
    }

    @SuppressWarnings("unchecked")
    static <A extends Attachable> A getAttachable(Object attached, Class<A> type) {
        Map<Class<? extends Attachable>, Attachable> map = ATTACHABLE_MAP.get(attached);
        if (map == null) {
            return null;
        } else {
            return (A) map.get(type);
        }
    }

    //////////////////////////////////////////////////////////////

    private Collection<? extends T> attached;

    private boolean disabled;

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    // 构造方法
    protected Attachable(Collection<? extends T> t) {
        this.attached = t;
        t.forEach(__ -> register(__, this));
    }

    public Collection<? extends T> getAttached() {
        return attached;
    }

    public T getAttachedOne() {
        return attached.stream().findFirst().orElse(null);
    }
}
