/*******************************************************************************
 * Copyright (C) 2011 Atlas of Living Australia
 * All Rights Reserved.
 * 
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 ******************************************************************************/
package au.org.ala.delta.intkey.directives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.org.ala.delta.intkey.directives.invocation.ContentsDirectiveInvocation;
import au.org.ala.delta.intkey.directives.invocation.BasicIntkeyDirectiveInvocation;
import au.org.ala.delta.intkey.model.IntkeyContext;
import au.org.ala.delta.intkey.ui.UIUtils;

public class ContentsDirective extends StandardIntkeyDirective {

    public ContentsDirective() {
        super(true, "contents");
    }

    @Override
    protected List<IntkeyDirectiveArgument<?>> generateArgumentsList(IntkeyContext context) {
        List<IntkeyDirectiveArgument<?>> arguments = new ArrayList<IntkeyDirectiveArgument<?>>();
        arguments.add(new FileArgument("file", UIUtils.getResourceString("ContentsFileChooserDescription.caption"), null, Arrays.asList(new String[] { "ind" }), false));
        return arguments;
    }

    @Override
    protected List<IntkeyDirectiveFlag> buildFlagsList() {
        return null;
    }

    @Override
    protected BasicIntkeyDirectiveInvocation buildCommandObject() {
        return new ContentsDirectiveInvocation();
    }

}
