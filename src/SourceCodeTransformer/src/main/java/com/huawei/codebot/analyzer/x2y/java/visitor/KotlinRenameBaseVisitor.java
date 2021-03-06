/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.codebot.analyzer.x2y.java.visitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.codebot.analyzer.x2y.global.commonvisitor.KotlinLocalVariablesVisitor;
import com.huawei.codebot.analyzer.x2y.java.RenameBaseChanger;
import com.huawei.codebot.codeparsing.kotlin.KotlinFile;
import com.huawei.codebot.framework.ChangeTrace;
import com.huawei.codebot.framework.model.DefectInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A specific ASTVisitor that relative with Kotlin rename. We use it to visit AST and match a node that need a rename
 * DefectInstance.
 *
 * @since 2020-04-21
 */
public class KotlinRenameBaseVisitor extends KotlinLocalVariablesVisitor {
    /**
     * A list used to store DefectInstance that detect by this visitor.
     */
    public List<DefectInstance> defectInstances = new ArrayList<>();
    /**
     * A map that key is the line number of buggyLine and value is a {@link ChangeTrace} of this buggyLine.
     */
    public Map<java.lang.Integer, ChangeTrace> line2Change;
    /**
     * The KotlinFile this visitor is visiting.
     */
    protected KotlinFile kotlinFile;
    /**
     * The changer that use the visitor to visit a AST.
     */
    protected RenameBaseChanger changer;
    /**
     * A map that key is the qualified name of ImportDeclaration and value is is the line number of ImportDeclaration.
     */
    protected Map<String, java.lang.Integer> importName2LineNumber;
    /**
     * Used to visit ImportDeclaration and create a lazy DefectInstance of ImportDeclaration conveniently.
     */
    protected ChangeIterator changeIterator;
    /**
     * Used to store a DefectInstance's description.
     */
    protected Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    protected KotlinRenameBaseVisitor(KotlinFile kotlinFile, RenameBaseChanger changer) {
        super();
        this.kotlinFile = kotlinFile;
        this.changer = changer;
        this.importName2LineNumber = new HashMap<>();
        this.line2Change = new HashMap<>();
        this.changeIterator = new ChangeIterator(this);
    }
}
