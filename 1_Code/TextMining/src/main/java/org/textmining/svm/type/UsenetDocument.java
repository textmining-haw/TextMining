

/* First created by JCasGen Fri May 08 12:01:16 CEST 2015 */
package org.textmining.svm.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed May 13 17:24:55 CEST 2015
 * XML source: /src/main/resources/org/textmining/svm/type/UsenetDocument.xml
 * @generated */
public class UsenetDocument extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(UsenetDocument.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected UsenetDocument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public UsenetDocument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public UsenetDocument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public UsenetDocument(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: category

  /** getter for category - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCategory() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "org.textmining.svm.type.UsenetDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_category);}
    
  /** setter for category - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCategory(String v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "org.textmining.svm.type.UsenetDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_category, v);}    
   
    
  //*--------------*
  //* Feature: probabilityPositiveKey

  /** getter for probabilityPositiveKey - gets 
   * @generated
   * @return value of the feature 
   */
  public String getProbabilityPositiveKey() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityPositiveKey == null)
      jcasType.jcas.throwFeatMissing("probabilityPositiveKey", "org.textmining.svm.type.UsenetDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityPositiveKey);}
    
  /** setter for probabilityPositiveKey - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbabilityPositiveKey(String v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityPositiveKey == null)
      jcasType.jcas.throwFeatMissing("probabilityPositiveKey", "org.textmining.svm.type.UsenetDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityPositiveKey, v);}    
   
    
  //*--------------*
  //* Feature: probabilityPositiveValue

  /** getter for probabilityPositiveValue - gets 
   * @generated
   * @return value of the feature 
   */
  public double getProbabilityPositiveValue() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityPositiveValue == null)
      jcasType.jcas.throwFeatMissing("probabilityPositiveValue", "org.textmining.svm.type.UsenetDocument");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityPositiveValue);}
    
  /** setter for probabilityPositiveValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbabilityPositiveValue(double v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityPositiveValue == null)
      jcasType.jcas.throwFeatMissing("probabilityPositiveValue", "org.textmining.svm.type.UsenetDocument");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityPositiveValue, v);}    
   
    
  //*--------------*
  //* Feature: probabilityNegativeKey

  /** getter for probabilityNegativeKey - gets 
   * @generated
   * @return value of the feature 
   */
  public String getProbabilityNegativeKey() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityNegativeKey == null)
      jcasType.jcas.throwFeatMissing("probabilityNegativeKey", "org.textmining.svm.type.UsenetDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityNegativeKey);}
    
  /** setter for probabilityNegativeKey - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbabilityNegativeKey(String v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityNegativeKey == null)
      jcasType.jcas.throwFeatMissing("probabilityNegativeKey", "org.textmining.svm.type.UsenetDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityNegativeKey, v);}    
   
    
  //*--------------*
  //* Feature: probabilityNegativeValue

  /** getter for probabilityNegativeValue - gets 
   * @generated
   * @return value of the feature 
   */
  public double getProbabilityNegativeValue() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityNegativeValue == null)
      jcasType.jcas.throwFeatMissing("probabilityNegativeValue", "org.textmining.svm.type.UsenetDocument");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityNegativeValue);}
    
  /** setter for probabilityNegativeValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbabilityNegativeValue(double v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_probabilityNegativeValue == null)
      jcasType.jcas.throwFeatMissing("probabilityNegativeValue", "org.textmining.svm.type.UsenetDocument");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_probabilityNegativeValue, v);}    
  }

    