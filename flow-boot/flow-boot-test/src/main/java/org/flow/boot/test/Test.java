package org.flow.boot.test;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

//        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
//
//        String s = sr.get();
//        System.out.println(s);
//        s = null;
//        System.gc();
//        System.out.println(sr.get());
        
        
        
        Class<?> clz =A.class;
        Object po = clz.newInstance();
        Field[] fields = clz.getFields();
        for (Field f : fields) {
            f.set(po, "宋印赠");
        }

    }
}

class A {
    public WeakReference<String> name;
}