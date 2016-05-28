package org.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GreetingTask extends DefaultTask {
    String greeting = 'main from GreetingTask'

    @TaskAction
    def greet() {
        println greeting
    }
}
