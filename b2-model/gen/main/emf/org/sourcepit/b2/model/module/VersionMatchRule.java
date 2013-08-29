/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Version Match Rule</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getVersionMatchRule()
 * @model
 * @generated
 */
public enum VersionMatchRule implements Enumerator
{
   /**
    * The '<em><b>Compatible</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #COMPATIBLE_VALUE
    * @generated
    * @ordered
    */
   COMPATIBLE(0, "compatible", "compatible"),

   /**
    * The '<em><b>Perfect</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #PERFECT_VALUE
    * @generated
    * @ordered
    */
   PERFECT(1, "perfect", "perfect"),

   /**
    * The '<em><b>Equivalent</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #EQUIVALENT_VALUE
    * @generated
    * @ordered
    */
   EQUIVALENT(2, "equivalent", "equivalent"),

   /**
    * The '<em><b>Greater Or Equal</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #GREATER_OR_EQUAL_VALUE
    * @generated
    * @ordered
    */
   GREATER_OR_EQUAL(3, "greaterOrEqual", "greaterOrEqual");

   /**
    * The '<em><b>Compatible</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Compatible</b></em>' literal object isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * @see #COMPATIBLE
    * @model name="compatible"
    * @generated
    * @ordered
    */
   public static final int COMPATIBLE_VALUE = 0;

   /**
    * The '<em><b>Perfect</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Perfect</b></em>' literal object isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * @see #PERFECT
    * @model name="perfect"
    * @generated
    * @ordered
    */
   public static final int PERFECT_VALUE = 1;

   /**
    * The '<em><b>Equivalent</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Equivalent</b></em>' literal object isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * @see #EQUIVALENT
    * @model name="equivalent"
    * @generated
    * @ordered
    */
   public static final int EQUIVALENT_VALUE = 2;

   /**
    * The '<em><b>Greater Or Equal</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Greater Or Equal</b></em>' literal object isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * @see #GREATER_OR_EQUAL
    * @model name="greaterOrEqual"
    * @generated
    * @ordered
    */
   public static final int GREATER_OR_EQUAL_VALUE = 3;

   /**
    * An array of all the '<em><b>Version Match Rule</b></em>' enumerators.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private static final VersionMatchRule[] VALUES_ARRAY = new VersionMatchRule[]
      {
         COMPATIBLE,
         PERFECT,
         EQUIVALENT,
         GREATER_OR_EQUAL,
      };

   /**
    * A public read-only list of all the '<em><b>Version Match Rule</b></em>' enumerators.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static final List<VersionMatchRule> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

   /**
    * Returns the '<em><b>Version Match Rule</b></em>' literal with the specified literal value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static VersionMatchRule get(String literal)
   {
      for (int i = 0; i < VALUES_ARRAY.length; ++i)
      {
         VersionMatchRule result = VALUES_ARRAY[i];
         if (result.toString().equals(literal))
         {
            return result;
         }
      }
      return null;
   }

   /**
    * Returns the '<em><b>Version Match Rule</b></em>' literal with the specified name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static VersionMatchRule getByName(String name)
   {
      for (int i = 0; i < VALUES_ARRAY.length; ++i)
      {
         VersionMatchRule result = VALUES_ARRAY[i];
         if (result.getName().equals(name))
         {
            return result;
         }
      }
      return null;
   }

   /**
    * Returns the '<em><b>Version Match Rule</b></em>' literal with the specified integer value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static VersionMatchRule get(int value)
   {
      switch (value)
      {
         case COMPATIBLE_VALUE: return COMPATIBLE;
         case PERFECT_VALUE: return PERFECT;
         case EQUIVALENT_VALUE: return EQUIVALENT;
         case GREATER_OR_EQUAL_VALUE: return GREATER_OR_EQUAL;
      }
      return null;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final int value;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final String name;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final String literal;

   /**
    * Only this class can construct instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private VersionMatchRule(int value, String name, String literal)
   {
      this.value = value;
      this.name = name;
      this.literal = literal;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public int getValue()
   {
     return value;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String getName()
   {
     return name;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String getLiteral()
   {
     return literal;
   }

   /**
    * Returns the literal value of the enumerator, which is its string representation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString()
   {
      return literal;
   }

} // VersionMatchRule
