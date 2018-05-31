package com.jason.manager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jason.utils.FileUtil;
import com.jason.utils.StringUtil;

/**
 * 基本管理器
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月31日 下午2:34:01
 */
public class ScriptManager {

	private static final Logger log = LogManager.getLogger(ScriptManager.class);

	private static ScriptManager instance = new ScriptManager();

	public String outDir = "d:\\javaTest";
	public String sourceDir = "D:\\myjava";

	public static ScriptManager getInstance() {
		return instance;
	}

	/**
	 * 加载Java文件
	 */
	public void loadJavaFile(String... source) {
		FileUtil.delDirectory(this.outDir);
		List<File> files = new ArrayList<>();
		FileUtil.getFiles(sourceDir, ".java", files, fileAbsolutePath -> {
			for (String s : source) {
				if (fileAbsolutePath.contains(s)) {
					return true;
				}
			}
			return false;
		});
		compileFile(files);
	}

	private void compileFile(List<File> files) {
		StringBuilder sb = new StringBuilder();
		if (null != files) {
			DiagnosticCollector<JavaFileObject> oDiagnosticCollector = new DiagnosticCollector<>();
			// 获取编译器实例
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// 获取标准文件管理器实例
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(oDiagnosticCollector, null,
					Charset.forName("utf-8"));
			try {
				// 没有java文件，直接返回
				if (files.isEmpty()) {
					log.error(this.sourceDir + "目录下查找不到任何java文件");
					return;
				}
				log.error("找到需要编译的文件共：" + files.size());
				// 创建输出目录，如果不存在的话
				new java.io.File(this.outDir).mkdirs();
				// 获取要编译的编译单元
				Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
				/**
				 * 编译选项，在编译java文件时，编译程序会自动的去寻找java文件引用的其他的java源文件或者class。
				 * -sourcepath选项就是定义java源文件的查找目录， -classpath选项就是定义class文件的查找目录。
				 */
				ArrayList<String> options = new ArrayList<>(0);
				options.add("-g");
				options.add("-source");
				options.add("1.8");
				// options.add("-Xlint");
				// options.add("unchecked");
				options.add("-encoding");
				options.add("UTF-8");
				options.add("-sourcepath");
				options.add(this.sourceDir); // 指定文件目录
				options.add("-d");
				options.add(this.outDir); // 指定输出目录

				ArrayList<File> jarsList = new ArrayList<>();
				FileUtil.getFiles(sourceDir + "\\target", ".jar", jarsList, null);
				String jarString = "";
				jarString = jarsList.stream().map((jar) -> jar.getPath() + File.pathSeparator).reduce(jarString,
						String::concat);
				// log.warn("jarString:" + jarString);
				if (!StringUtil.isEmpty(jarString)) {
					options.add("-classpath");
					options.add(jarString);// 指定附加的jar包
				}

				JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, oDiagnosticCollector,
						options, null, compilationUnits);
				// 运行编译任务
				Boolean call = compilationTask.call();
				if (!call) {
					oDiagnosticCollector.getDiagnostics().forEach(f -> {
						sb.append(";").append(((JavaFileObject) (f.getSource())).getName()).append(" line:")
								.append(f.getLineNumber());
						log.error("加载文件错误：" + ((JavaFileObject) (f.getSource())).getName() + " line:"
								+ f.getLineNumber());
					});
				}
			} catch (Exception ex) {
				sb.append(this.sourceDir).append("错误：").append(ex);
				log.error("加载文件错误：", ex);
			} finally {
				try {
					fileManager.close();
				} catch (IOException ex) {
					log.error("", ex);
				}
			}
		} else {
			log.error(this.sourceDir + "目录下查找不到任何java文件");
		}
	}

	public static void main(String[] args) {
		ScriptManager.getInstance().loadJavaFile("D:\\myjava");
	}
}
