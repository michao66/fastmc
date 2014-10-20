package cn.fastmc.core;



import java.io.InputStream;
import java.net.URL;

/**
 * 针对CLASSS 级文件 载入类
 * @author michao
 *
 */
public class ClassLoaderWrapper {

  ClassLoader defaultClassLoader;

  ClassLoaderWrapper() {
  }


  public URL getResourceAsURL(String resource) {
    return getResourceAsURL(resource, new ClassLoader[]{
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }


  public URL getResourceAsURL(String resource, ClassLoader classLoader) {
    return getResourceAsURL(resource, new ClassLoader[]{
        classLoader,
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }


  public InputStream getResourceAsStream(String resource) {
    return getResourceAsStream(resource, new ClassLoader[]{
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }


  public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
    return getResourceAsStream(resource, new ClassLoader[]{
        classLoader,
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }


  public Class classForName(String name) throws ClassNotFoundException {
    return classForName(name, new ClassLoader[]{
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }


  public Class classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
    return classForName(name, new ClassLoader[]{
        classLoader,
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        ClassLoader.getSystemClassLoader()
    });
  }

 
  InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
    for (ClassLoader cl : classLoader) {
      if (null != cl) {

        // try to find the resource as passed
        InputStream returnValue = cl.getResourceAsStream(resource);

        // now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
        if (null == returnValue) returnValue = cl.getResourceAsStream("/" + resource);

        if (null != returnValue) return returnValue;
      }
    }
    return null;
  }


  URL getResourceAsURL(String resource, ClassLoader[] classLoader) {

    URL url;

    for (ClassLoader cl : classLoader) {

      if (null != cl) {

        // look for the resource as passed in...
        url = cl.getResource(resource);

        // ...but some class loaders want this leading "/", so we'll add it
        // and try again if we didn't find the resource
        if (null == url) url = cl.getResource("/" + resource);

        // "It's always in the last place I look for it!"
        // ... because only an idiot would keep looking for it after finding it, so stop looking already.
        if (null != url) return url;

      }

    }

    // didn't find it anywhere.
    return null;

  }


  Class classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {
    for (ClassLoader cl : classLoader) {
      if (null != cl) {
        try {

          Class c = cl.loadClass(name);

          if (null != c) return c;

        } catch (ClassNotFoundException e) {
          // we'll ignore this until all classloaders fail to locate the class
        }
      }
    }
    throw new ClassNotFoundException("Cannot find class: " + name);
  }

}
