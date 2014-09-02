package com.haxwell.apps.questions.utils;

/**
 * Copyright 2013,2014 Johnathan E. James - haxwell.org - jj-ccs.com - quizki.com
 *
 * This file is part of Quizki.
 *
 * Quizki is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Quizki is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quizki. If not, see http://www.gnu.org/licenses.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haxwell.apps.questions.entities.AbstractEntity;
import com.haxwell.apps.questions.factories.ValidatorFactory;
import com.haxwell.apps.questions.validators.Validator;

public class ValidationUtil {

	public static String validate(AbstractEntity e) {
		
		String rtn = "";
		Map<String, List<String>> errors = new HashMap<String, List<String>>();
		
		Validator validator = ValidatorFactory.getValidator(e);
		validator.validate(e, errors);

		rtn = CollectionUtil.toJSON(errors);
		
		return rtn;
	}
}
