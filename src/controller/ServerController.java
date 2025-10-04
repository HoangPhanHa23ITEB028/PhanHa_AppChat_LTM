//
////package controller;
////
////import java.io.BufferedReader;
////import java.io.BufferedWriter;
////import java.io.IOException;
////import java.io.InputStreamReader;
////import java.io.OutputStreamWriter;
////import java.net.Socket;
////
////import javafx.scene.layout.VBox;
////
////public class ServerController {
////	private Socket socket;
////	private BufferedWriter out;
////	private BufferedReader in;
////	private BufferedWriter clientPrintWriter;
////
////	public ServerController(Socket socket, BufferedWriter clientPrintWriter) {
////		this.socket = socket;
////		this.clientPrintWriter = clientPrintWriter;
////	}
////
////	public ServerController() {
////		try {
////			in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
////			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
////
//////			synchronized (clientPrintWriter) {
//////				clientPrintWriter.
//////			}
////
////		} catch (IOException exception) {
////			exception.printStackTrace();
////		} finally {
////			close(socket, in, out);
////		}
////	}
////
////	public void sendtoServer(String message) {
////		try {
////			out.write(message);
////			out.newLine();
////			out.flush();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////			close(socket, in, out);
////		}
////	}
////
////	public void receiveFromServer(VBox vbox) {
////		new Thread(new Runnable() {
////
////			@Override
////			public void run() {
////				String message;
////
////				try {
////					while ((message = in.readLine()) != null) {
////
////						ClientController clientController = new ClientController();
////						clientController.onSendAndReceiveMessenge(message, vbox, false);
////
////					}
////				} catch (IOException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////					close(socket, in, out);
////
////				}
////			}
////		}).start();
////
////	}
////
////	public void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
////		try {
////			if (bufferedWriter != null) {
////				bufferedWriter.close();
////			}
////			if (bufferedReader != null) {
////				bufferedReader.close();
////			}
////			if (socket != null) {
////				socket.close();
////			}
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////
////}
//
//package controller;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.util.Vector;
//
//import com.fatboyindustrial.gsonjavatime.Converters;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import controller.Handler.LoginHandler;
//import controller.Handler.RegisterHandler;
//import model.FileInfo;
//import model.Packet;
//import service.RedisOnlineService;
//import service.UserService;
//import util.RedisUtil;
//
////Client handle
//public class ServerController implements Runnable {
//	private Socket socket;
//	private BufferedReader in;
//	private BufferedWriter out;
//	public Gson gson;
//
//	private RedisOnlineService redisOnlineService;
//	private UserService userService;
//
//	// Danh sách tất cả client đang kết nối (dùng Vector để quản lý)
//	private static Vector<ServerController> clients = new Vector<>();
//
//	public ServerController(Socket socket) {
//		try {
//			this.socket = socket;
//			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			gson = Converters.registerAll(new GsonBuilder()).setDateFormat("EEE MMM dd HH:mm:ss z yyyy").create();
//			this.redisOnlineService = new RedisOnlineService(RedisUtil.getClient());
//			this.userService = new UserService();
//
//			clients.add(this); // thêm client vào danh sách
//
//			System.out.println("Client connected: " + socket.getInetAddress());
//
//		} catch (IOException e) {
//			close();
//		}
//	}
//
//	@Override
//	public void run() {
//		try {
//			String message;
//			while ((message = in.readLine()) != null) {
//				System.out.println("Received: " + message);
//
//				Packet packet = gson.fromJson(message, Packet.class);
//
//				System.out.println(packet.getType());
//				switch (packet.getType()) {
//				case "LOGIN": {
//					LoginHandler loginHandler=new LoginHandler(userService,gson);
//					Packet loginResponse = loginHandler.handle(packet);
//					sendSelfClient(loginResponse);
//					break;
//				}
//				case "REGISTER": {
//					RegisterHandler registerHandler=new RegisterHandler(userService,gson);
//					Packet registerReponse = registerHandler.handle(packet);
//					sendSelfClient(registerReponse);
//					break;
//				}
//				 case "FILE": {
//			            FileInfo fileData = gson.fromJson(
//			                gson.toJson(packet.getData()), FileInfo.class
//			            );
//			            nhanfile(fileData);
//			            break;
//			        }
//				default:
//					broadcast(message); // gửi cho tất cả client khác	
//				}
//			}
//		} catch (IOException e) {
//			close();
//		}
//	}
//	private void nhanfile(FileInfo fileData) {
//	    try {
//	       
//	        File dir = new File("uploads");
//	        if (!dir.exists()) {
//	            dir.mkdirs(); // tạo cả cây thư mục 
//	        }
//	        //server Chuẩn bị lưu file
//	        File file = new File(dir, fileData.getFileName());
//
//	        System.out.println("File sẽ lưu ở: " + file.getAbsolutePath());
//
//	        FileOutputStream fos = new FileOutputStream(file);
//
//	        // Đọc bytes từ socket (không dùng BufferedReader!)
//	        byte[] buffer = new byte[4096];
//	        long remaining = fileData.getFileSize();
//
//	        var rawIn = socket.getInputStream();
//	        while (remaining > 0) {
//	            int read = rawIn.read(buffer, 0, (int) Math.min(buffer.length, remaining));
//	            if (read == -1) break;
//	            fos.write(buffer, 0, read);
//	            remaining -= read;
//	        }
//	        fos.close();
//	        broadcastFile(fileData,file);
//	        System.out.println("Đã nhận file: " + file.getAbsolutePath());
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
//
//	private void sendSelfClient(Packet packet) throws IOException {
//		this.out.write(gson.toJson(packet));
//		this.out.newLine();
//		this.out.flush();
//	}
//
//	// Gửi tin nhắn cho tất cả client trong Vector
//	private void broadcast(String message) {
//		for (ServerController client : clients) {
//			if (client != this) {
//				try {
//					if (client.socket.isClosed())
//						continue;
//
//					client.out.write(message);
//					client.out.newLine();
//					client.out.flush();
//				} catch (IOException e) {
//					client.close();
//				}
//			}
//		}
//	}
//	private void broadcastFile(FileInfo fileData, File file) {
//	    for (ServerController client : clients) {
//	        if (client != this) {
//	            try {
//	                // Gửi metadata 
//	                Packet packet = new Packet();
//	                packet.setType("FILE");
//	                packet.setData(fileData);
//	                client.out.write(gson.toJson(packet));
//	                client.out.newLine();
//	                client.out.flush();
//
//	                // Gửi bytes file
//	                try (FileInputStream fis = new FileInputStream(file)) {
//	                    byte[] buffer = new byte[4096];
//	                    int count;
//	                    while ((count = fis.read(buffer)) > 0) {
//	                        client.socket.getOutputStream().write(buffer, 0, count);
//	                    }
//	                    client.socket.getOutputStream().flush();
//	                }
//
//	            } catch (IOException e) {
//	                client.close();
//	            }
//	        }
//	    }
//	}
//
//
//
//	// Đóng kết nối
//	private void close() {
//		try {
//			if (in != null)
//				in.close();
//			if (out != null)
//				out.close();
//			if (socket != null)
//				socket.close();
//
//			clients.remove(this);
//
//			System.out.println("Client disconnected.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
