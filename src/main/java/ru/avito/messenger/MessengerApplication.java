package ru.avito.messenger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MessengerApplication.class, args);

		TelegramBot telegramBot = new TelegramBot("5323373538:AAHJzaXOhAln_osOa7U-5nPZQJLS8ktjyMI");
		telegramBot.setUpdatesListener(updates ->{
			for (Update update : updates) {
				telegramBot.execute(new SendMessage(update.message().chat().id(), update.message().text()));
			}

			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});

	}


}
