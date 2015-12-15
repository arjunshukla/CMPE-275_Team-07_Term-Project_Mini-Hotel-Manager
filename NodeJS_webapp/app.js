var express = require('express'),
    http = require('http'),
    https = require('https'),
    path = require('path'),
    util = require('util'),
    ejs = require('ejs');

var app = express();

app.use(express.static(__dirname + '/client'));
// app.use((express.static(__dirname + '/client/js')));
app.set('views', __dirname + '/client');
app.use('/js', express.static(__dirname, +'/client/js'));
app.use('/css', express.static(__dirname, +'/client/css'));
app.set('view engine', 'ejs');
app.set('view engine', 'html');
app.engine('html', require('ejs').__express);
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);


var request = require("request");

var endpoint = "http://52.24.2.53:8080/CmpE275Team07Fall2015TermProject/v1";

// app.use(express.static(path.join(__dirname, '/client')));

//Requests

app.get("/index", function(req, res) {
    res.render('index');
});

app.get("/searchRoomsAvailability", function(req, res) {
    console.log("In search rooms function");

});

app.get("/login", function(req, res) {
    res.render('login');
});

// Search Reservation----- arpit
app.post("/postReservationForm", function(req, res) {
    console.log(req.body);

    var options = {
        method: 'POST',
        url: endpoint + "/searchRoomAvailability/",
        body: {
            "checkin_date": req.body.checkin_date,
            "checkout_date": req.body.checkout_date,
            "number_of_rooms": req.body.noOfRooms,
            "room_type": req.body.room_type
        },
        json: true,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
            if (error || response.statusCode != 200) {
                res.send("Invalid Inputs");
            }
            else if (response.statusCode == 200 && body == null) {
                res.send("No rooms available for specified dates!");
            }
            else if (response.statusCode == 200) {
                console.log("Rooms Available for reservation");
                var strError = "";
                var availableRooms = [];
                var roomString = "";
                var checkin_date = req.body.checkin_date;
                var checkout_date = req.body.checkout_date;
                var requestedCount = req.body.number_of_rooms;
                var availableCount = body.length;

                if (requestedCount == 1) {
                    roomString = roomString + body[0].room_no;
                    availableRooms[0] = body[0];
                    checkin_date[0] = body[0];
                    checkout_date[0] = body[0];

                }
                // 1. Number of requested rooms = number available rooms
                else if (requestedCount == availableCount) {
                    for (var i = 0; i < requestedCount; i++) {
                        availableRooms[i] = body[i];
                        checkin_date[i] = body[i];
                        checkout_date[i] = body[i];
                        if (roomString == "")
                            roomString = body[i].room_no + ",";
                        else if (i == requestedCount - 1)
                            roomString = roomString + body[i].room_no;
                        else
                            roomString = roomString + body[i].room_no + ","
                    }
                }
                // 2. Number of requested rooms > number available rooms
                else if (requestedCount > availableCount) {
                    for (var i = 0; i < availableCount; i++) {
                        availableRooms[i] = body[i];
                        checkin_date[i] = body[i];
                        checkout_date[i] = body[i];
                        if (roomString == "")
                            roomString = body[i].room_no + ",";
                        else if (i == availableCount - 1)
                            roomString = roomString + body[i].room_no;
                        else
                            roomString = roomString + body[i].room_no + ","
                    }
                    strError = "Sorry, only " + availableCount + " rooms are available for the given dates and requested room type";
                }
                // 3. Number of requested rooms < number available rooms
                else if (requestedCount < availableCount) {
                    for (var i = 0; i < requestedCount; i++) {
                        availableRooms[i] = body[i];
                        checkin_date[i] = body[i];
                        checkout_date[i] = body[i];
                        if (roomString == "")
                            roomString = body[i].room_no + ",";
                        else if (i == requestedCount - 1)
                            roomString = roomString + body[i].room_no;
                        else
                            roomString = roomString + body[i].room_no + ","
                    }
                }
                console.log(roomString);

                res.render('availableRooms', {
                    availableRooms: availableRooms,
                    roomString: roomString,
                    strError: strError,
                    checkin_date: checkin_date,
                    checkout_date: checkout_date
                });
            }
    });
});

app.get("reservation", function(req, res) {
    res.render('reservation');
});



//----------------------------------------------------------Tejas workspace starts here---------------------------------------------------------------------------------
app.get("/report", function(req, res) {
    res.render('report');
});

app.get("/postDateForReport/:date?", function(req, res) {
    var request = require("request");

    var options = {
        method: 'GET',
        url: endpoint + "/report/" + req.params.date,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
        if (error) throw new Error(error);

        var dateValue = req.params.date;
        //  var htmlCodeTORender="/* start of html for report*/
        var htmlCodeToRender = "\
<html> \
<head> \
<script type='text/javascript' src='https://www.google.com/jsapi'> </script> \
<script src='https://code.jquery.com/jquery-1.7.1.min.js'> </script> \
<script type='text/javascript' data-json_value=" + body + " data-req_date=" + dateValue + " src='/js/generateReport.js'> </script> \
</head> \
<body> \
<div id='chart_div'></div> \
</body> \
</html>";

        res.send(htmlCodeToRender);
        /* end of html render for report*/
        // res.render("report", {
        //     jsonData: body
        // });
        console.log(body);
    });

});

app.get("/loadchartiframe", function(req, res) {
    res.send("<h1>Please select a date to load piechart.</h1>");
});

//----------------------------------------------------------Tejas workspace ends here------------------------------------------------------------------------------------





/*-----------------------------------------------------------Sneha's Space - Working on Admin------------------------------------------------------*/

/*Loads checkin */
app.get("/checkin", function(req, res) {
    res.render('checkin');
})

/*Loads gallery */
app.get("/hotel_gallery", function(req, res) {
    res.render('hotel_gallery');
})

/*Loads verifyReservation */
app.get("/verifyReservation", function(req, res) {
    res.render('verifyReservation');
})

app.post("/reservationcheck", function(req, res) {
    console.log("In checkin guest");
    var options = {
        method: 'GET',
        url: endpoint + "/reservationcheck/" + req.body.reservation_token + "/" + req.body.license_no,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
        if (error || response.statusCode != 200) {
            res.send("Invalid License or Reservation ID OR Guest does not exist");
        }
        else if (response.statusCode == 200) {
            console.log("Guests exists");
            var guestsExists = [];
            guestsExists = JSON.parse(body);

            //res.send("Guests Exists!!!" + "/n" + body);
            res.render("guestsExists", {
                guestsExists: guestsExists
            });
        }

        console.log(body);
    });

});

app.post("/checkinGuest", function(req, res) {
    var options = {
        method: 'PUT',
        url: endpoint + "/checkinGuest",
        headers: {
            'content-type': 'application/json'
        },
        body: [{
            "reservation_id": req.body.reservation_id,
            "room_no": req.body.room_no,
            "guest_count": req.body.guest_count,
            "checkin_date": req.body.checkin_date,
            "checkout_date": req.body.checkout_date,
            "mappingId": req.body.mappingId,
            "number_of_rooms": null,
            "room_type": null
        }],
        json: true
    };

    request(options, function(error, response, body) {
        if (error || response.statusCode != 200) {
            res.send(error);
        }
        else if (response.statusCode == 200) {
            console.log("In Guest checkin");
            res.send("Guests Checked In !!!");
        }

        console.log(body);
    });
});

/*Loads checkout */
app.get("/checkout", function(req, res) {
    var resultStr = "";
    res.render('checkout', {
        resultStr: resultStr
    });
})

/*Do checkout*/
app.post("/checkoutGuest", function(req, res) {
    var options = {
        method: 'PUT',
        url: endpoint + "/checkoutGuest/" + req.body.reservation_token,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
        var resultStr = "";
        if (error || response.statusCode != 200) {
            resultStr = "Checkout Failed!!!"
            res.render('checkout', {
                resultStr: resultStr
            });
        }
        else if (response.statusCode == 200) {
            resultStr = "Guest successfully checked out! Room No:  " + req.body.reservation_token + " is now available for further reservations";
            console.log("In guest checkout !!!");
            res.render('checkout', {
                resultStr: resultStr
            });
        }
        console.log(body);
    });
});

/*Loads reservation */
app.get("/reservation", function(req, res) {
    res.render('reservation');
})

/*Loads addRoom */
app.get("/addRoom", function(req, res) {
    var resultStr = "";
    res.render('addRoom', {
        resultStr: resultStr
    });
})

/* Adds a room*/

/*Loads deleteRoom */
app.get("/deleteRoom", function(req, res) {
    var resultStr = "";
    res.render('deleteRoom', {
        resultStr: resultStr
    });
});

//delete a room
app.post("/delete", function(req, res) {
    console.log("In delete room");

    var options = {
        method: 'DELETE',
        url: endpoint + "/room/" + req.body.room_no,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
        var resultStr = "";
        if (response.statusCode == 200) {

            resultStr = "Room with number: " + req.body.room_no + " deleted successfully";
            console.log("Room deleted");
            res.render('deleteRoom', {
                resultStr: resultStr
            });
        }
        else if (response.statusCode == 409) {
            resultStr = "Room cannot be deleted as it might have existing reservations!";
            console.log("Room already exists");
            res.render('deleteRoom', {
                resultStr: resultStr
            });
        }
        else if (error) {
            resultStr = "Error adding room";
            console.log("Error: " + error);
            res.render('deleteRoom', {
                resultStr: resultStr
            });
        }
        console.log(body);
    });
});


/*Loads when Admin user clicks on addRoom */
app.get("/addroom", function(req, res) {
    res.render('room');
});

app.post("/addingroom", function(req, res) {
    console.log("In add a Room function");
    var options = {
        method: 'POST',
        url: endpoint + "/room",
        headers: {
            'content-type': 'application/json'
        },
        body: {
            room_no: req.body.room_no,
            room_type: req.body.room_type,
            room_status: req.body.room_status
        },
        json: true
    };

    request(options, function(error, response, body) {
        var resultStr = "";
        if (response.statusCode == 201) {

            resultStr = "Room with number: " + req.body.room_no + " added successfully";
            console.log("Room created");
            res.render('addRoom', {
                resultStr: resultStr
            });
        }
        else if (response.statusCode == 409) {
            resultStr = "Room Number already exists! Please try with a different Room No!";
            console.log("Room already exists");
            res.render('addRoom', {
                resultStr: resultStr
            });
        }
        else if (error) {
            resultStr = "Error adding room";
            console.log("Error: " + error);
            res.render('addRoom', {
                resultStr: resultStr
            });
        }
        console.log(body);
        //res.send(body);
    });
});

/*----------------------------------------------------------------------------Sneha's Space ends here---------------------------------------------------*/

app.get("/t", function(req, res) {
    res.render('talkToUs_updated');
});

/* Put code for rooms search*/
app.get("/searchRoomsAvailability", function(req, res) {

});

//make rerservation: booking
app.get("/makeReservation", function(req, res) {
    var room_nos = "";
    var room_count = "";
    var resultStr = "";
    var checkin_date = "";
    var checkout_date = "";
    res.render('makeReservation', {
        "room_nos": room_nos,
        "room_count": room_count,
        "resultStr": resultStr,
        "checkin_date": checkin_date,
        "checkout_date": checkout_date
    });

});

//make rerservation for room number: booking
app.post("/bookRoom", function(req, res) {
    var resultStr = "";
    console.log(req.body);
    res.render('makeReservation', {
        room_nos: req.body.room_nos,
        room_count: req.body.room_count,
        resultStr: resultStr,
        checkin_date: req.body.checkin_date,
        checkout_date: req.body.checkout_date
    });

});


//Book Reservations

app.post("/makeReservation", function(req, res) {
    var options = {
        method: 'POST',
        url: endpoint + "/makeReservations",
        qs: {
            "checkin_date": req.body.checkin_date,
            "checkout_date": req.body.checkout_date,
            "no_of_rooms": req.body.no_of_rooms
        },
        headers: {
            'content-type': 'application/json'
        },
        body: {
            "guest_name": req.body.guest_name,
            "guest_email": req.body.guest_email,
            "license_no": req.body.license_no,
            "street": req.body.street,
            "city": req.body.city,
            "zip": req.body.zip,
            "state": req.body.state,
            room_no_selected: req.body.room_no_selected
        },
        json: true
    };

    request(options, function(error, response, body) {
        var resultStr = "";
        var room_nos = "";
        var room_count = "";
        if (error || response.statusCode != 200) {
            resultStr = "Invalid Request OR Duplicate License No!";
            res.render("makeReservation", {
                resultStr: resultStr,
                room_nos: room_nos,
                room_count: room_count
            });
        }
        else if (response.statusCode == 200) {
            // res.send("Rooms booked. Please find your Reservation_token");
            var strReserved = "Rooms booked. Your Reservation token is: " + body;
            res.render("reserved", {
                strReserved: strReserved
            })
        }
        console.log(body);
    });

    // var options = { method: 'POST',
    //  url: endpoint+'/makeReservations',
    //  qs: 
    //   { checkin_date: '2015-12-24',
    //     checkout_date: '2015-12-29',
    //     no_of_rooms: '2' },
    //  headers: 
    //   {  'content-type': 'application/json' },
    //  body: 
    //   { guest_name: 'Arjun Shukla123',
    //     guest_email: 'arju2232ds3@email',
    //     license_no: 'Licensedfere999',
    //     street: '190 ryland',
    //     city: 'san jose',
    //     zip: 95110,
    //     state: 'CA',
    //     room_no_selected: '101,102' },
    //  json: true };

    // request(options, function (error, response, body) {
    //  if (error) throw new Error(error);

    //  console.log(body);
    //  res.send(body);
    // });
})

app.get("/checkout", function(req, res) {
    var resultStr = "";
    res.render("billing", {
        resultStr: resultStr
    });
})

//Billing page called
app.post("/billing", function(req, res) {
    // res.render('billing');


    var options = {
        method: 'PUT',
        url: endpoint + "/checkoutGuest/" + req.body.reservation_token,
        headers: {
            'content-type': 'application/json'
        }
    };

    request(options, function(error, response, body) {
        var resultStr = "";
        if (error || response.statusCode != 200) {
            resultStr = "Checkout Failed!!!";
            res.render('checkout', {
                resultStr: resultStr
            });
        }
        else if (response.statusCode == 200) {
            resultStr = "Guest successfully checked out!";
            console.log("In guest checkout !!!");
            // res.render('checkout', {
            //     resultStr: resultStr
            // });

            var options = {
                method: 'GET',
                url: endpoint + "/billing",
                qs: {
                    "reservation_token": req.body.reservation_token,
                    "discount": req.body.discount,
                    "user_name": req.body.user_name
                },
                headers: {
                    'content-type': 'application/json'
                }
            };

            request(options, function(error, response, body) {
                var resultStr = "";
                if (error || response.statusCode != 200) {
                    resultStr = "Invalid Request";
                    res.render("billing", {
                        resultStr: resultStr
                    });
                }
                else if (response.statusCode == 200) {
                    // res.send("Rooms booked. Please find your Reservation_token");
                    var jsonBody = JSON.parse(body);
                    var billAmount = jsonBody;
                    res.render("generatedBill", {
                        billAmount: jsonBody[0].bill_amount,
                        billBody: jsonBody[0].bill_body
                    });
                }
                console.log(body);
            });
        }
    });
});

//Chart page called
app.get("/chart", function(req, res) {
    res.render('chart');

});

/* Validate user login whether an Admin or Service Agent*/
app.post("/loginUser", function(req, res) {
    console.log(req.body);


    var options = {
        method: 'POST',
        url: endpoint + "/login",
        headers: {
            'content-type': 'application/json'
        },
        body: {
            user_name: req.body.user_name,
            password: req.body.password
        },
        json: true
    };

    request(options, function(error, response, body) {
        if (body === "Invalid username or password") {
            console.log("Invalid");
            res.send("Invalid Username or Password");
        }
        else if (body === 'Admin') {
            console.log("In Admin");
            res.render("Admin_updated", {
                userType: "Administrator"
            });
        }

        else if (body === 'Service Agent') {
            console.log("In Service Agent");
            res.render('SA', {
                userType: "Service Agent"
            });
        }
        console.log(body);
    });

});

// app.post('/sendEmail', function(req, res) {
//     console.log(req.body.userEmail);
//     // Use Smtp Protocol to send Email
//     // var smtpTransport = mailer.createTransport("SMTP",{
//     //     host: 'smtp.pallyapp.com',
//     //     port: 25,
//     //     auth: {
//     //         user: 'team@pallyapp.com',
//     //         pass: 'brooklyn88'
//     //     }
//     // });

//     var mail = {
//         from: "Pally <team@pallyapp.com>",
//         to: req.body.userEmail,
//         subject: "Thank You!",
//         text: "Hi there!\nThank you for signing up to Pally! We'll be launching to the public soon. Sit tight for our updates! Oh, and we promise to never spam you with useless information.\nWith love,\nTeam Pally",
//         html: fs.readFileSync('./client/thankYouSignup').toString()
//     };

//     smtpTransport.sendMail(mail, function(error, response) {
//         if (error) {
//             console.error(error.stack);
//         }
//         else {
//             console.log("Message sent: " + response.message);
//         }

//         smtpTransport.close();
//     });
//     // subscriber(req.body);
//     res.render("index");
// });

// app.post('/sendFeedback', function(req, res) {
//     console.log(req.body.userEmail);

//     var mail = {
//         from: "Pally <team@pallyapp.com>",
//         to: req.body.userEmail,
//         subject: "Thank You for your Feedback!",
//         text: "Hi there!\nThank you for your interest in Pally! One of our team members will review your message and get back to you as soon as possible (usually within 24 hours).\nIn the meantime, please tell your friends about Pally!\nSincerely,\nTeam Pally",
//         html: fs.readFileSync('./client/thankYouFeedback').toString()
//     };

//     smtpTransport.sendMail(mail, function(error, response) {
//         if (error) {
//             console.error(error.stack);
//         }
//         else {
//             console.log("Message sent: " + response.message);
//         }

//         smtpTransport.close();
//     });
//     // var html = res.render('feedback',{name:req.body.firstName});
//     var mailToTeam = {
//         from: "Pally <team@pallyapp.com>",
//         to: "team@pallyapp.com",
//         subject: "Feedback for Pally",
//         text: "",
//         html: "<div class='rcmBody'>\
//     <table width='590' border='0' align='center' cellpadding='0' cellspacing='0' style='border: solid 1px #dfdfdf; background: repeat center top'>\
//         <tbody>\
//             <tr>\
//                 <td valign='top'>\
//                     <table width='100%' border='0' cellspacing='0' cellpadding='15'>\
//                         <tbody>\
//                             <tr>\
//                                 <td valign='top' style='font-family: Arial, Tahoma; font-size: 13px; color: #484848; line-height: 18px'>\
//                                     <h2 style='color: #A2BC8D'>Thank You</h2>\
//                                     <table>\
//                                         <tbody>\
//                                             <tr>\
//                                                 <td>Hi Team!\
//                                                     <br>\
//                                                     <br> There is a new feedback from our user " + req.body.firstName + " \
//                                                     <br> <b>First Name:</b> " + req.body.firstName + " <b> Last Name:</b>  " + req.body.lastName + "\
//                                                     <br> <b>Email:</b> " + req.body.userEmail + "\
//                                                     <br> <b>Mobile:</b> " + req.body.mobileNumber + "\
//                                                     <br><br> <b>Message:</b> " + req.body.message + "\
//                                                     <br>\
//                                                     <br>\
//                                                     <br>Thanks,\
//                                                     <br>Team Pally</td>\
//                                             </tr>\
//                                         </tbody>\
//                                     </table>\
//                                 </td>\
//                             </tr>\
//                         </tbody>\
//                     </table>\
//                 </td>\
//             </tr>\
//             <tr>\
//                 <td valign='top' style='border-top: solid 1px #dfdfdf; padding: 10px'>\
//                     <table width='100%' border='0' cellspacing='0' cellpadding='0'>\
//                         <tbody>\
//                             <tr>\
//                                 <td width='56%' valign='middle'>\
//                                 </td>\
//                                 <td width='44%' align='right' valign='middle' style='font-family: arial, tahoma; color: #afafb0; font-size: 11px'>Copyrights 2015 pallyapp.com. All Rights Reserved.</td>\
//                             </tr>\
//                         </tbody>\
//                     </table>\
//                 </td>\
//             </tr>\
//         </tbody>\
//     </table>\
// </div>"
//     };

//     smtpTransport.sendMail(mailToTeam, function(error, response) {
//         if (error) {
//             console.error(error.stack);
//         }
//         else {
//             console.log("Message sent: " + response.message);
//         }

//         smtpTransport.close();
//     });



//     res.render("index");
// });

var server = app.listen(8080, function() {
    console.log("Listening on port %d", server.address().port);
});