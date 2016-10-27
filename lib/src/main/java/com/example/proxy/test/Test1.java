package com.example.proxy.test;

import com.example.proxy.Moveable;
import com.example.proxy.Tank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Created by wuxiaojun on 16-7-23.
 */
public class Test1 {

    public static void main(String[] args) throws Exception {

        String rt = "\r\n";

        String src = "package com.example.proxy.test;"+rt+
                "import com.example.proxy.Moveable;"+rt+
                "public class TankTimeProxy implements Moveable {"+rt+

                "public TankTimeProxy(Moveable t){this.t = t;}"+rt+

                "Moveable t;"+rt+

                "@Override"+rt+
                "public void move(){long start = befor();t.move();after(start);}"+rt+

                "@Override"+rt+
                "public void stop() {long start = befor();t.stop();after(start);}"+

                "private long befor(){long start = System.currentTimeMillis();System.out.println(\"start time:\"+start);return start;}"+

                "private void after(long startTime){long end = System.currentTimeMillis();System.out.println(\"end time:=\" + (end - startTime));}"+

                "}" ;
        //获取当前类的路径
        String fileName = System.getProperty("user.dir")+
                "/lib/src/main/java/com/example/proxy/test/TankTimeProxy.java";
        System.out.println("fileName="+fileName);

        File f = new File(fileName);
        FileWriter fw = new FileWriter(f);
        fw.write(src);
        fw.flush();
        fw.close();
        //获取java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        System.out.println("名称是"+compiler.getClass().getName());
        //管理的动态生成的文件
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable unitss = standardFileManager.getJavaFileObjects(fileName);
        //获取编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, unitss);
        task.call();
        standardFileManager.close();
        //直接编译成class文件

        //load into memory and create an instance
        //从下面这个目录下找class文件
        URL[] urls = new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/src")};
        URLClassLoader ul = new URLClassLoader(urls);
        Class c = ul.loadClass("com.example.proxy.test.TankTimeProxy");
        System.out.println("c=" + c.getName());
        //找一个参数为Moveable的构造方法
        Constructor ctr = c.getConstructor(Moveable.class);
        Moveable m = (Moveable) ctr.newInstance(new Tank());
        m.move();

    }

}
