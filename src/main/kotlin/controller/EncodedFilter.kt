package controller

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter

@WebFilter(urlPatterns = ["/"])
class EncodedFilter : Filter {
    private val encoding = "UTF-8"

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request.characterEncoding = encoding
        response.characterEncoding = encoding
        chain.doFilter(request, response)
    }
}