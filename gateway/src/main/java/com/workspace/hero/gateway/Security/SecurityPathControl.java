package com.workspace.hero.gateway.Security;

import org.springframework.stereotype.Component;

@Component
public class SecurityPathControl {

    public void checkAccess(String path, String role){

        if(path.contains("/booking/workspace/create") && !role.equals("MANAGER")){
            throw new IllegalStateException("Only manager can create a workspace");
        }


    }
}
