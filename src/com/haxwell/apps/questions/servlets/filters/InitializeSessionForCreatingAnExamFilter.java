package com.haxwell.apps.questions.servlets.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.haxwell.apps.questions.constants.Constants;
import com.haxwell.apps.questions.constants.DifficultyConstants;
import com.haxwell.apps.questions.entities.Exam;
import com.haxwell.apps.questions.entities.Question;
import com.haxwell.apps.questions.entities.User;
import com.haxwell.apps.questions.managers.ExamManager;

/**
 * Makes sure the necessary objects are in the session for the code downstream.
 * 
 */
@WebFilter("/InitializeSessionForCreatingAnExamFilter")
public class InitializeSessionForCreatingAnExamFilter extends AbstractFilter {

    public InitializeSessionForCreatingAnExamFilter() { /* do nothing */ }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Logger log = Logger.getLogger(InitializeSessionForCreatingAnExamFilter.class.getName());
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = ((HttpServletRequest)request);
			HttpSession session = req.getSession();

			String examId = req.getParameter("examId");
			
			if (examId != null) {
				Exam exam = ExamManager.getExam(Integer.parseInt(examId));
				
				User user = (User)session.getAttribute(Constants.CURRENT_USER_ENTITY);
				
				if (user == null || exam.getUser().getId() != user.getId()) {
					request.setAttribute("doNotAllowEntityEditing", Boolean.TRUE);
				}
				else {
					session.setAttribute(Constants.CURRENT_EXAM, exam);
					
					// Remove the questions already on the exam from the list of questions to be displayed.. no need allowing them to be selected again
					Collection<Question> coll = (Collection<Question>)session.getAttribute(Constants.LIST_OF_QUESTIONS_TO_BE_DISPLAYED);
					
					if (coll != null) {
						coll.removeAll(exam.getQuestions());
						session.setAttribute(Constants.LIST_OF_QUESTIONS_TO_BE_DISPLAYED, coll);
					}
				}
			}
			
			session.setAttribute(Constants.MRU_FILTER_DIFFICULTY, DifficultyConstants.GURU);
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}
