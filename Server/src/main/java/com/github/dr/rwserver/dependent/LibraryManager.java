package com.github.dr.rwserver.dependent;

import com.github.dr.rwserver.data.global.Data;
import com.github.dr.rwserver.struct.Seq;
import com.github.dr.rwserver.util.file.FileUtil;
import com.github.dr.rwserver.util.log.Log;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarFile;

import static com.github.dr.rwserver.net.HttpRequest.downUrl;
import static com.github.dr.rwserver.util.IsUtil.notIsBlank;

/**
 * @author Dr
 */
public class LibraryManager {
	private static Instrumentation inst = null;
    private final String URL;
    private final String PATH;
    private final Seq<ImportData> dependencies = new Seq<>();
    private final Seq<File> load = new Seq<>();


    /** JRE将在启动main()之前调用方法 */
    public static void agentmain(final String a, final Instrumentation inst) {
        LibraryManager.inst = inst;
    }

    public LibraryManager(String path) {
        URL = "https://maven.aliyun.com/nexus/content/groups/public";
        this.PATH = path;
    }

    public LibraryManager(boolean china,String path) {
        if (china) {
            URL = "https://maven.aliyun.com/nexus/content/groups/public";
        } else {
            URL = "https://repo1.maven.org/maven2";
        }
        this.PATH = FileUtil.File(path).getPath();
    }

    /**
     * 加载并添加到URLClassLoader
     */
    public final void loadToClassLoader() {
        Log.clog(Data.localeUtil.getinput("server.load.jar"));
        load();
        try {
            if (ClassLoader.getSystemClassLoader() instanceof URLClassLoader) {
                Method f = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                f.setAccessible(true);
                load.each(e -> {
                    try {
                        f.invoke(ClassLoader.getSystemClassLoader(), e.toURI().toURL());
                        Log.info("Load Lib Jar",e.getName());
                    } catch (Exception classLoad) {
                        Log.error("Jar 1.8 Load",classLoad);
                    }
                });
            } else {
                load.each(e -> {
                    try {
                        inst.appendToSystemClassLoaderSearch(new JarFile(e));
                        Log.info("Load Lib Jar",e.getName());
                    } catch (Exception classLoad) {
                        Log.error("Jar 1.8+ Load",classLoad);
                    }
                });
            }
        } catch (Exception e) {
            Log.fatal("Class Load",e);
            Log.clog(Data.localeUtil.getinput("load.err"));
        }
    }

    private Seq<File> load() {
        dependencies.each(e -> {
            if (e.downFile.exists()) {
                load.add(e.downFile);
            } else {
                if (notIsBlank(e.downUrl)) {
                    if (downUrl(e.downUrl,e.downFile)) {
                        load.add(e.downFile);
                    }
                } else {
                    load.add(e.downFile);
                }
            }
        });
        return load;
    }

    public final void customImportLib(final String name, final String version) {
        String savePath = PATH + "/" +
                name + "-" +
                version + ".jar";
        dependencies.add(new ImportData(null, savePath));
    }

    public final void customImportLib(final String downUrl, final String name, final String version) {
        String url = downUrl + "/" +
                name + "-" +
                version + ".jar";
        String savePath = PATH + "/" +
                name + "-" +
                version + ".jar";
        dependencies.add(new ImportData(url, savePath));
    }

    public final void importLib(final String str, final String name, final String version) {
        final String[] temp = str.split("\\.");
        final StringBuilder url = new StringBuilder(URL);
        url.append("/");
        for (String s : temp) {
            url.append(s).append("/");
        }
        url.append(name).append("/")
            .append(version).append("/")
            .append(name).append("-")
            .append(version).append(".jar");
        String savePath = PATH + "/" +
                name + "-" +
                version + ".jar";
        dependencies.add(new ImportData(url.toString(), savePath));
    }

    public final void removeOldLib() {
        FileUtil fileUtil = new FileUtil(new File(PATH));
        List<File> list =  fileUtil.getFileList();
        for (File file : list) {
            if (!load.contains(file)) {
                file.delete();
            }
        }
    }

    private static class ImportData {
        final String downUrl;
        final File downFile;
        public ImportData(String downUrl,String downFile) {
            this.downUrl = downUrl;
            this.downFile = new File(downFile);
        }
    }
}