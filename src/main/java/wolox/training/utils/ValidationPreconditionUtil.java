package wolox.training.utils;

import com.google.common.base.Preconditions;
import wolox.training.constant.MessageValidation;

public class ValidationPreconditionUtil {

	public static void validateFieldCheckNotNull(Object object, String nameFailed){
		Preconditions.checkNotNull(object, nameFailed + " " + MessageValidation.PRECONDITION_MESSAGE);
	}

}
