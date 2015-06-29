
/* First created by JCasGen Fri May 08 12:01:16 CEST 2015 */
package org.textmining.svm.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed May 13 17:24:55 CEST 2015
 * @generated */
public class UsenetDocument_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (UsenetDocument_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = UsenetDocument_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new UsenetDocument(addr, UsenetDocument_Type.this);
  			   UsenetDocument_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new UsenetDocument(addr, UsenetDocument_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = UsenetDocument.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.textmining.svm.type.UsenetDocument");
 
  /** @generated */
  final Feature casFeat_category;
  /** @generated */
  final int     casFeatCode_category;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCategory(int addr) {
        if (featOkTst && casFeat_category == null)
      jcas.throwFeatMissing("category", "org.textmining.svm.type.UsenetDocument");
    return ll_cas.ll_getStringValue(addr, casFeatCode_category);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCategory(int addr, String v) {
        if (featOkTst && casFeat_category == null)
      jcas.throwFeatMissing("category", "org.textmining.svm.type.UsenetDocument");
    ll_cas.ll_setStringValue(addr, casFeatCode_category, v);}
    
  
 
  /** @generated */
  final Feature casFeat_probabilityPositiveKey;
  /** @generated */
  final int     casFeatCode_probabilityPositiveKey;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getProbabilityPositiveKey(int addr) {
        if (featOkTst && casFeat_probabilityPositiveKey == null)
      jcas.throwFeatMissing("probabilityPositiveKey", "org.textmining.svm.type.UsenetDocument");
    return ll_cas.ll_getStringValue(addr, casFeatCode_probabilityPositiveKey);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProbabilityPositiveKey(int addr, String v) {
        if (featOkTst && casFeat_probabilityPositiveKey == null)
      jcas.throwFeatMissing("probabilityPositiveKey", "org.textmining.svm.type.UsenetDocument");
    ll_cas.ll_setStringValue(addr, casFeatCode_probabilityPositiveKey, v);}
    
  
 
  /** @generated */
  final Feature casFeat_probabilityPositiveValue;
  /** @generated */
  final int     casFeatCode_probabilityPositiveValue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getProbabilityPositiveValue(int addr) {
        if (featOkTst && casFeat_probabilityPositiveValue == null)
      jcas.throwFeatMissing("probabilityPositiveValue", "org.textmining.svm.type.UsenetDocument");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_probabilityPositiveValue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProbabilityPositiveValue(int addr, double v) {
        if (featOkTst && casFeat_probabilityPositiveValue == null)
      jcas.throwFeatMissing("probabilityPositiveValue", "org.textmining.svm.type.UsenetDocument");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_probabilityPositiveValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_probabilityNegativeKey;
  /** @generated */
  final int     casFeatCode_probabilityNegativeKey;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getProbabilityNegativeKey(int addr) {
        if (featOkTst && casFeat_probabilityNegativeKey == null)
      jcas.throwFeatMissing("probabilityNegativeKey", "org.textmining.svm.type.UsenetDocument");
    return ll_cas.ll_getStringValue(addr, casFeatCode_probabilityNegativeKey);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProbabilityNegativeKey(int addr, String v) {
        if (featOkTst && casFeat_probabilityNegativeKey == null)
      jcas.throwFeatMissing("probabilityNegativeKey", "org.textmining.svm.type.UsenetDocument");
    ll_cas.ll_setStringValue(addr, casFeatCode_probabilityNegativeKey, v);}
    
  
 
  /** @generated */
  final Feature casFeat_probabilityNegativeValue;
  /** @generated */
  final int     casFeatCode_probabilityNegativeValue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getProbabilityNegativeValue(int addr) {
        if (featOkTst && casFeat_probabilityNegativeValue == null)
      jcas.throwFeatMissing("probabilityNegativeValue", "org.textmining.svm.type.UsenetDocument");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_probabilityNegativeValue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProbabilityNegativeValue(int addr, double v) {
        if (featOkTst && casFeat_probabilityNegativeValue == null)
      jcas.throwFeatMissing("probabilityNegativeValue", "org.textmining.svm.type.UsenetDocument");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_probabilityNegativeValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public UsenetDocument_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_category = jcas.getRequiredFeatureDE(casType, "category", "uima.cas.String", featOkTst);
    casFeatCode_category  = (null == casFeat_category) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_category).getCode();

 
    casFeat_probabilityPositiveKey = jcas.getRequiredFeatureDE(casType, "probabilityPositiveKey", "uima.cas.String", featOkTst);
    casFeatCode_probabilityPositiveKey  = (null == casFeat_probabilityPositiveKey) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probabilityPositiveKey).getCode();

 
    casFeat_probabilityPositiveValue = jcas.getRequiredFeatureDE(casType, "probabilityPositiveValue", "uima.cas.Double", featOkTst);
    casFeatCode_probabilityPositiveValue  = (null == casFeat_probabilityPositiveValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probabilityPositiveValue).getCode();

 
    casFeat_probabilityNegativeKey = jcas.getRequiredFeatureDE(casType, "probabilityNegativeKey", "uima.cas.String", featOkTst);
    casFeatCode_probabilityNegativeKey  = (null == casFeat_probabilityNegativeKey) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probabilityNegativeKey).getCode();

 
    casFeat_probabilityNegativeValue = jcas.getRequiredFeatureDE(casType, "probabilityNegativeValue", "uima.cas.Double", featOkTst);
    casFeatCode_probabilityNegativeValue  = (null == casFeat_probabilityNegativeValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probabilityNegativeValue).getCode();

  }
}



    