package com.labequiped.userservice.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
		String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
		// System.out.println(GREEN + "Application Service Registry is Running ..." + RESET);
		System.out.println(GREEN +
                "  _____   _    _   _   _   _   _   ___    _   _    ____     _   _   \n" +
                " |  __ \\ | |  | | | \\ | | | \\ | | |_ _|  | \\ | |  / ___|   | \\ | |  \n" +
                " | |__) || |  | | |  \\| | |  \\| |  | |   |  \\| | | |  _    |  \\| |  \n" +
                " |  _  / | |  | | | . ` | | . ` |  | |   | . ` | | |_| |   | . ` |  \n" +
                " | | \\ \\ | |__| | | |\\  | | |\\  |  | |   | |\\  | |  _  |   | |\\  |  \n" +
                " |_|  \\_\\ \\____/  |_| \\_| |_| \\_| |___|  |_| \\_| |_| |_|   |_| \\_|  \n" +
                "                                                                    \n" +
                RESET);
	}

}
