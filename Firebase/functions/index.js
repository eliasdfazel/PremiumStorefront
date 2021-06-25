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