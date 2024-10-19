import pytest


class HttpRequest:
    def __init__(self, request_type, resource_path, http_protocol):
        self.request_type = request_type
        self.resource_path = resource_path
        self.http_protocol = http_protocol


def reqstr2obj(request_string):
    """Function gets text HTTP request and returns HTTP request object"""
    pass


def test_reqstr2obj_type_error():
    with pytest.raises(TypeError):
        reqstr2obj(123)


def reqstr2obj(request_string):
    """Function gets text HTTP request and returns HTTP request object"""
    if not isinstance(request_string, str):
        raise TypeError("request_string must be a string")


def test_returns_http_request():
    result = reqstr2obj("GET / HTTP1.1")
    assert isinstance(result, HttpRequest)


def test_returns_correct_http_request():
    result = reqstr2obj("GET / HTTP1.1")
    assert result.request_type == "GET"
    assert result.resource_path == "/"
    assert result.http_protocol == "HTTP1.1"


@pytest.mark.parametrize("result_string, type, path,protocol",
                         [("PUT /about HTTP2.0", "PUT", "/about", "HTTP2.0"),
                          ("POST /home HTTP1.1", "POST", "/home", "HTTP1.1")])
def test_handles_different_arguments(result_string, type, path, protocol):
    result = reqstr2obj(result_string)
    assert result.request_type == type
    assert result.resource_path == path
    assert result.http_protocol == protocol


def test_returns_none_for_invalid_input():
    assert reqstr2obj("GET/") is None
    assert reqstr2obj("GET /HTTP1.1") is None


def test_raises_bad_request_type_error():
    with pytest.raises(BadRequestTypeError):
        reqstr2obj("DOWNLOAD /movie.mp4 HTTP1.1")


def test_raises_bad_http_version():
    with pytest.raises(BadHTTPVersion):
        reqstr2obj("GET /movie.mp4 HTTP1.2")


def test_raises_value_error_on_bad_path():
    with pytest.raises(ValueError, match="Path must start with /"):
        reqstr2obj("GET movie.mp4 HTTP1.1")


del reqstr2obj


# rewriting the function to pass the tests one by one
def reqstr2obj(request_string):
    """Function gets text HTTP request and returns HTTP request object"""
    if not isinstance(request_string, str):
        raise TypeError("request_string must be a string")

    parts = request_string.split()
    if len(parts) != 3:
        return None

    request_type, resource_path, http_protocol = parts

    if not resource_path.startswith("/"):
        raise ValueError("Path must start with /")

    if request_type not in {"GET", "POST", "PUT", "DELETE"}:
        raise BadRequestTypeError(f"Illegal request type: {request_type}")

    if http_protocol not in {"HTTP1.0", "HTTP1.1", "HTTP2.0"}:
        raise BadHTTPVersion(f"Bad HTTP version: {http_protocol}")

    return HttpRequest(request_type, resource_path, http_protocol)


class BadRequestTypeError(Exception):
    """Exception raised for invalid request type."""
    pass


class BadHTTPVersion(Exception):
    """Exception raised for invalid HTTP version."""
    pass
