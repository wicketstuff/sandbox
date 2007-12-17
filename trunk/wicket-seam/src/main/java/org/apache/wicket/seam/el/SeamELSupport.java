/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wicket.seam.el;

import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import org.jboss.seam.el.EL;

/**
 * Plugs the JBoss EL expression language and Seam
 * EL resolvers into Wicket.
 * Based on Jboss code.
 * 
 * @author Gavin King
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 *
 */
public class SeamELSupport {

    @SuppressWarnings("unchecked")
    public static <T> T evaluate(String expression, Class<T> returnType) {
        return (T)evaluate(expression, returnType, EL.EL_RESOLVER);
    }
    
    public static Object evaluate(
        String expression, Class returnType, final ELResolver resolver) 
        throws ELException {
        return createExpression(expression, returnType).evaluate(resolver);
    }
    
    public static Expression parseExpression(
        final String expression, final Class returnType)
        throws ELException {
        return createExpression(expression, returnType);
    }
    
    private static Expression createExpression(
        final String expression, final Class returnType) {
        
        return new Expression() {
            
            private MethodExpression me;
            
            private ValueExpression ve; 

            private void initMethodExpression() {
                me = EL.EXPRESSION_FACTORY.createMethodExpression(
                    EL.EL_CONTEXT, expression, returnType, new Class[0]);
            }

            private void initValueExpression() {
                ve = EL.EXPRESSION_FACTORY.createValueExpression(
                    EL.EL_CONTEXT, expression, returnType);
            }

            public Object evaluate(ELResolver resolver) throws ELException
            {
                try {
                    try {
                        if (me==null && ve==null) {
                            initMethodExpression();
                        }
                        if (me!=null && ve==null) {
                            return me.invoke(EL.EL_CONTEXT, new Object[0]);
                        }
                    } catch (javax.el.ELException e) {                                    
                        if (ve==null) {
                            initValueExpression();
                        }
                        if (ve!=null) {
                            return ve.getValue(EL.EL_CONTEXT);
                        }
                    }
                    throw new ELException();
                } 
                catch (javax.el.ELException e) {
                    throw new ELException(e);
                }
            }
        };
    }
    
}
