package com.backend.componet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientComponent {

    @Value("${inserted}")
    public String SuccessRegister;
    @Value("${notInserted}")
    public String ErrorRegister;
    @Value("${notFound}")
    public String NotFound;
    @Value("${updated}")
    public String Updated;
    @Value("${notUpdated}")
    public String NotUpdated;

}
