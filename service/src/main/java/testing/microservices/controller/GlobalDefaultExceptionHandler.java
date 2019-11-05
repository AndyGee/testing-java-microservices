package testing.microservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
  private static final String DEFAULT_ERROR_VIEW = "error";
  private static final String URL_ATTRIBUTE = "url";
  private static final String ERROR_ATTRIBUTE = "error";
  private static final String STATUS_ATTRIBUTE = "status";

  private Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

  @ExceptionHandler(value = NoSuchElementException.class)
  public ModelAndView handleNotFound(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

    checkResponseStatusException(e);

    logger.warn(e.getClass().getSimpleName(), e);

    response.setStatus(HttpStatus.NOT_FOUND.value());
    return buildModelAndView(request, HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  public ModelAndView handleBadRequest(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

    checkResponseStatusException(e);

    logger.warn(e.getClass().getSimpleName(), e);

    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return buildModelAndView(request, HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(value = Exception.class)
  public ModelAndView handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

    checkResponseStatusException(e);

    logger.warn(e.getClass().getSimpleName(), e);

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return buildModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  private void checkResponseStatusException(Exception e) throws Exception {
    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }
  }

  private ModelAndView buildModelAndView(HttpServletRequest request, HttpStatus status, Exception e) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName(DEFAULT_ERROR_VIEW);
    mav.setStatus(status);
    mav.addObject(URL_ATTRIBUTE, request.getRequestURL());
    mav.addObject(ERROR_ATTRIBUTE, e.getMessage());
    mav.addObject(STATUS_ATTRIBUTE, status);
    return mav;
  }
}
