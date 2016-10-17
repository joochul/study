<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Spring Security Example</title>
        
        <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    <script src="/webjars/jquery/jquery.min.js"></script>
	    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
	    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
	    <script src="/app.js"></script>
	    
	    <script>
	    var stompClient = null;

	    function setConnected(connected) {
	        $("#connect").prop("disabled", connected);
	        $("#disconnect").prop("disabled", !connected);
	        if (connected) {
	            $("#conversation").show();
	        }
	        else {
	            $("#conversation").hide();
	        }
	        $("#greetings").html("");
	    }

	    function connect() {
	        var socket = new SockJS('/sample-websocket');
	        
	        alert("connect");
	        
	        stompClient = Stomp.over(socket);
	        stompClient.connect({}, function (frame) {
	            setConnected(true);
	            
	            console.log('Connected: ' + frame);
	            
	            stompClient.subscribe('/topic/greetings', 
	            		function (greeting) {
	                		showGreeting(JSON.parse(greeting.body).content);
	            		}
	            );
	            
	        });
	    }

	    function disconnect() {
	        if (stompClient != null) {
	            stompClient.disconnect();
	        }
	        setConnected(false);
	        console.log("Disconnected");
	    }

	    function sendName() {
	    	alert("hello");
	        stompClient.send("/apptopic/topichello", {}, JSON.stringify({'name': $("#name").val()}));
	    }

	    function showGreeting(message) {
	        $("#greetings").append("<tr><td>" + message + "</td></tr>");
	    }

	    $(function () {
	        
	        $( "#connect" ).click(function() { connect(); });
	        $( "#disconnect" ).click(function() { disconnect(); });
	        $( "#send" ).click(function() { sendName(); });
	    });


	    </script>
	    
        
    </head>
    <body>
        <h1>Welcome!</h1>

        <p>Click <a href="<spring:url value='/web/admin' />">[ADMIN]</a> to see a greeting.</p>
        <br>
        <p>Click <a href="<spring:url value='/web/main' />">[MAIN]</a> to see a greeting.</p>
        
        <br><br>
        <p><a href="<spring:url value='/login' />">LOGIN</a></p>
        
        <div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
        
        
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" class="btn btn-default" >Connect</button>
                    <button id="disconnect" class="btn btn-default" disabled="disabled">Disconnect</button>
                </div>
            
            
        </div>
        <div class="col-md-6">
        
        
                <div class="form-group">
                    <label for="name">What is your name?</label>
                    <input type="text" id="name" class="form-control" placeholder="Your name here...">
                </div>
                <button id="send" class="btn btn-default">Send</button>
            
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                
                <tbody id="greetings">
                </tbody>
                
            </table>
        </div>
    </div>
    
</div>
        
    </body>
</html>