/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.shared.main;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PojoTest {
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 24;

    // The package to test
    private static final String POJO_PACKAGE = "com.googlecode.fspotcloud.shared.peer.rpc.actions";
    private List<PojoClass> pojoClasses;
    private PojoValidator pojoValidator;
    private Class<?>[] POJO_CLASSES = new Class<?>[]{
            AuthenticationAction.class, AuthenticationResult.class, DeleteUserGroupAction.class,
            GetMyUserGroupsAction.class,GetMyUserGroupsResult.class,  GetUserGroupAction.class, GetUserGroupResult.class,
            GrantUserAction.class, LogoutAction.class,
            GetTagTreeAction.class, GetUserInfo.class, PhotoInfo.class,
            PhotoInfoStore.class, TagNode.class, TagTreeResult.class,
            RequestFullsizeImageAction.class, RevokeTagAction.class, RevokeUserAction.class,
            NewUserGroupAction.class, SaveUserGroupAction.class, SignUpAction.class, SignUpResult.class,
            UserGroupInfo.class,
            UserInfo.class
    };

    @Before
    public void setup() {
        pojoClasses = newArrayList();

        for (Class pojo : POJO_CLASSES) {
            pojoClasses.add(PojoClassFactory.getPojoClass(pojo));
        }

        pojoValidator = new PojoValidator();
        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicFieldsRule());
        //pojoValidator.addRule(new NoPrimitivesRule());
        //pojoValidator.addRule(new NoStaticExceptFinalRule());
        //pojoValidator.addRule(new GetterMustExistRule());
        //pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new NoNestedClassRule());
        //pojoValidator.addRule(new BusinessKeyMustExistRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
        //pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());

        //pojoValidator.addTester(new BusinessIdentityTester());
    }

    @Test
    public void ensureExpectedPojoCount() {
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT,
                pojoClasses.size());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        for (PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }
}
