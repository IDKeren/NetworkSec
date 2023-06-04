package net.controllers;
//package net.controllers;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import demo2.Message;
//import demo2.logic.MessageService;
//
//@RestController
//public class AnotherController {
//	private MessageService messages;
//	
//	@Autowired
//	public void setMessages(MessageService messages) {
//		this.messages = messages;
//	}
//	
////	@RequestMapping(
////			path = {"/hello/{firstName}/{lastName}"},
////			method = {RequestMethod.GET},
////			produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Message hello (
//			@PathVariable("firstName") String firstName,
//			@PathVariable("lastName") String lastName) {
//		// TODO implement this using the business logic
//		throw new RuntimeException("not implemented yet");
//	}
//		
//	@RequestMapping(
//			path = {"/hello"},
//			method = {RequestMethod.POST},
//			produces = {MediaType.APPLICATION_JSON_VALUE},
//			consumes = {MediaType.APPLICATION_JSON_VALUE})
//	public Message createMessage (@RequestBody Message message){
//		return this.messages.addToDb(message);
//	}
//	
//	@RequestMapping(
//			path = {"/hello/{id}"},
//			method = {RequestMethod.PUT},
//			consumes = {MediaType.APPLICATION_JSON_VALUE})
//	public void updateSpecificMessage (
//			@PathVariable("id") Long id,
//			@RequestBody Message update) {
//		this.messages.updateMessage(id, update);
//	}
//		
//	@RequestMapping(
//			path = "/hello/{messageId}",
//			method = {RequestMethod.DELETE})
//	public void deleteMessageById(
//			@PathVariable("messageId") Long messageId) {
//		this.messages.deleteMessageById(messageId);
//	}
//		
//	@RequestMapping(
//			path = "/hello",
//			method = {RequestMethod.DELETE})
//	public void deleteAllMessages() {
//		this.messages.deleteAllMesages();
//	}
//}
//
//
//
//
//
//
//
//
