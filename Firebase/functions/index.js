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
            UpdateDataKey: 'ApplicationsData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessGames = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'GamesData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessBooks = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'BooksData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessMovies = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'MoviesData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.transferApplicationsData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=99'
        + '&category=80', true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {
  
        var jsonParserResponseText = JSON.parse(xmlHttpRequest.responseText);

        

    };
    xmlHttpRequest.send();

});

exports.transferGamesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {



});