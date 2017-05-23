/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: UselessEntity 
* @Description: 
* @author JY.Kim 
* @date 2017. 5. 17. 오전 9:25:10 
*  
*/
public class UselessEntity extends ValidatorOption{
	public enum Type {

		USELESSPOINT("UselessEntity", "GeometricError");

		String errName;
		String errType;

		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		public String errName() {
			return errName;
		}

		public String errType() {
			return errType;
		}
	};
}
