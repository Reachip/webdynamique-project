package com.tuto.springboot.SPWebAppStep2.rest;

import com.tuto.springboot.SPWebAppStep2.model.Hero;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestRestCrt {
    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello Hero !!!";
    }

    @RequestMapping(method= RequestMethod.POST,value="/addhero")
    public void addHero(@RequestBody Hero hero) {
        System.out.println(hero);
    }

    @RequestMapping(method=RequestMethod.GET,value="/msg/{id1}/{id2}")
    public String getMsg(@PathVariable String id1, @PathVariable String id2) {
        String msg1=id1;
        String msg2=id2;
        return "Composed Message: msg1:"+msg1+"msg2:"+msg2;
    }

    @RequestMapping(method=RequestMethod.GET,value="/parameters")
    public String getInfoParam(@RequestParam String param1,@RequestParam String param2) {
        return "Parameters: param1:"+param1+"param2:"+param2;
    }
}
