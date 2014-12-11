import org.codehaus.gant.GantBinding

includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript('_GrailsPackage')

eventCompileStart = {
    compileScalaSource()
}

private void compileScalaSource() {


    println "Start: Compiling Scala Source Code"

    ant.path(id: "scala.compile.classpath") {
        path(refid: "grails.compile.classpath")
    }

    ant.taskdef(name: 'scalac', classname: 'scala.tools.ant.Scalac', classpathref: "scala.compile.classpath")

    def scalaClassPath = classesDirPath


    try {
        def scalaSrcEncoding = 'UTF-8'

        println "Compiling Scala sources to $scalaClassPath"
        ant.delete(dir: scalaClassPath)
        ant.mkdir(dir: scalaClassPath)

        //1st scala compiler pass
        ant.scalac(destdir: scalaClassPath,
                classpathref: "scala.compile.classpath",
                encoding: scalaSrcEncoding) {

            if (new File("${basedir}/src/scala").exists()) {
                src(path: "${basedir}/src/scala")
            }

            if (new File("${basedir}/src/java").exists()) {
                src(path: "${basedir}/src/java")
            }
        }

        //compiling java classes to source dir
        ant.javac(srcdir: "${basedir}/src/scala", destdir: scalaClassPath,
                target: "${buildConfig.grails.project.source.level}",
                classpathref: "scala.compile.classpath",
                includeantruntime: false)

        //2nd pass with scala compiler
        ant.scalac(destdir: scalaClassPath,
                classpathref: "scala.compile.classpath",
                encoding: scalaSrcEncoding) {

            if (new File("${basedir}/src/scala").exists()) {
                src(path: "${basedir}/src/scala")
            }

            if (new File("${basedir}/src/java").exists()) {
                src(path: "${basedir}/src/java")
            }

        }

    } catch (Exception e) {
        Ant.fail(message: "Could not compile Scala sources: " + e.class.simpleName + ": " + e.message)
    }

    println "Completed: Compiling Scala Source Code"
}
