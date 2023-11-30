package com.tuling.crypto;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author:zhgw
 * @Date:16:42,2023/10/26
 */
public class CASTest {
    static class Entity {
        private int i;
    }

    public static void main(String[] args) {
        Entity entity = new Entity();
        Unsafe unsafe = getUnsafe();
        long index = getFieldIndex(unsafe, Entity.class, "i");
        boolean flag = unsafe.compareAndSwapInt(entity, index, 0, 2);
        flag = unsafe.compareAndSwapInt(entity, index, 2, 3);
        flag = unsafe.compareAndSwapInt(entity, index, 2, 4);
    }

    private static Unsafe getUnsafe() {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            return (Unsafe) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Long getFieldIndex(Unsafe unsafe, Class clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

}
