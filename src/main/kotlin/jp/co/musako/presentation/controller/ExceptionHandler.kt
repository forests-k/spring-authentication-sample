package jp.co.musako.presentation.controller

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.*

@ControllerAdvice
class ExceptionHandler {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleError(e: Exception, response: HttpServletResponse): Map<String, Any> {
        val errorMap = HashMap<String, Any>()
        errorMap.put("message", e.localizedMessage)
        errorMap.put("status", INTERNAL_SERVER_ERROR)
        return errorMap;
    }

}