/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.plugins.ide.eclipse.model;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import groovy.util.Node;
import groovy.util.NodeList;
import org.gradle.internal.xml.XmlTransformer;
import org.gradle.plugins.ide.eclipse.model.internal.FileReferenceFactory;
import org.gradle.plugins.ide.internal.generator.XmlPersistableConfigurationObject;

import java.util.List;
import java.util.Set;

/**
 * Represents the customizable elements of an eclipse classpath file. (via XML hooks everything is customizable).
 */
public class Classpath extends XmlPersistableConfigurationObject {
    private final FileReferenceFactory fileReferenceFactory;
    private List<ClasspathEntry> entries = Lists.newArrayList();

    public Classpath(XmlTransformer xmlTransformer, FileReferenceFactory fileReferenceFactory) {
        super(xmlTransformer);
        this.fileReferenceFactory = fileReferenceFactory;
    }

    public Classpath() {
        super(new XmlTransformer());
        fileReferenceFactory = null;
    }

    public List<ClasspathEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ClasspathEntry> entries) {
        this.entries = entries;
    }

    @Override
    protected String getDefaultResourceName() {
        return "defaultClasspath.xml";
    }

    @Override
    protected void load(Node xml) {
        for (Object e : (NodeList)xml.get("classpathentry")) {
            Node entryNode = (Node) e;
            Object kind = entryNode.attribute("kind");
            if ("src".equals(kind)) {
                String path = (String) entryNode.attribute("path");
                entries.add(path.startsWith("/") ? new ProjectDependency(entryNode) : new SourceFolder(entryNode));
            } else if ("var".equals(kind)) {
                entries.add(new Variable(entryNode, fileReferenceFactory));
            } else if ("con".equals(kind)) {
                entries.add(new Container(entryNode));
            } else if ("lib".equals(kind)) {
                entries.add(new Library(entryNode, fileReferenceFactory));
            } else if ("output".equals(kind)) {
                entries.add(new Output(entryNode));
            }
        }
    }

    public List<ClasspathEntry> configure(List<ClasspathEntry> newEntries) {
        Set<ClasspathEntry> updatedEntries = Sets.newLinkedHashSet();
        for (ClasspathEntry entry : entries) {
            if (!isDependency(entry) && !isJreContainer(entry)) {
                updatedEntries.add(entry);
            }
        }
        updatedEntries.addAll(newEntries);
        return entries =  Lists.newArrayList(updatedEntries);
    }

    @Override
    protected void store(Node xml) {
        NodeList classpathEntryNodes = (NodeList)xml.get("classpathentry");
        for (Object classpathEntry : classpathEntryNodes) {
            xml.remove((Node) classpathEntry);
        }
        for (ClasspathEntry entry : entries) {
            entry.appendNode(xml);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classpath classpath = (Classpath) o;
        return Objects.equal(entries, classpath.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entries);
    }

    @Override
    public String toString() {
        return "Classpath{entries=" + entries + "}";
    }

    private boolean isDependency(ClasspathEntry entry) {
        return entry instanceof ProjectDependency || entry instanceof AbstractLibrary;
    }

    private boolean isJreContainer(ClasspathEntry entry) {
        return entry instanceof Container && ((Container) entry).getPath().startsWith("org.eclipse.jdt.launching.JRE_CONTAINER");
    }
}
