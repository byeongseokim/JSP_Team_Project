package com.example.api;

import java.io.IOException;
import java.util.List;

public interface ApiProvider {
    //api 제공기능
    List<?> apiProvide() throws IOException;
}
