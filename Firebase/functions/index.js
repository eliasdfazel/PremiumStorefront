const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp({
    credential: admin.credential.applicationDefault(),
});


const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const runtimeOptions = {
    timeoutSeconds: 313,
}

exports.triggerBackgroundUpdatingProcessApplications = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: "ApplicationsData",
        },
    };

    res.send(message);

    /*admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });*/

});