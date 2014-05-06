package org.mybatis.caches.memcached;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Created with IntelliJ IDEA.
 * User: gabriel
 * Date: 4/6/14
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
class ContextClassLoaderObjectInputStream extends ObjectInputStream {
    public ContextClassLoaderObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader.loadClass(desc.getName());
        } else {
            return super.resolveClass(desc);
        }
    }
}
