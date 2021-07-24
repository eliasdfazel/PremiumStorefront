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

    const IdKey = "id"

    const NameKey = "name"
    const DescriptionKey = "description"
    const SummaryKey = "short_description"

    const RegularPriceKey = "regular_price"
    const SalePriceKey = "sale_price"

    const CategoriesKey = "categories"
    const TagsKey = "tags"

    const ImagesKey = "images"
    const ImageKey = "image"
    const ImageSourceKey = "src"

    const AttributesKey = "attributes"
    const AttributeOptionsKey = "options"

    const AttributesPackageNameKey = "Package Name"

    const AttributesAndroidCompatibilitiesKey = "Android Compatibilies"
    const AttributesContentSafetyRatingKey = "Content Safety Rating"

    const AttributesDeveloperEmailKey = "Developer Email"
    const AttributesDeveloperCountryKey = "Developer Country"
    const AttributesDeveloperStateKey = "Developer State"

    const AttributesDeveloperCityKey = "Developer City"
    const AttributesDeveloperNameKey = "Developer Name"
    const AttributesDeveloperWebsiteKey = "Developer Website"

    const AttributesRatingKey = "Rating"
    const AttributesYoutubeIntroductionKey = "Youtube Introduction"

    const AttributesVerticalArtKey = "Vertical Art"

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=80';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
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

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        jsonArrayParserResponse.forEach((jsonObject) => {

            var applicationName = jsonObject[NameKey];

            /* Start - Images */
            var featuredContentImages = jsonObject[ImagesKey];

            var productIcon = (featuredContentImages[0])[ImageSourceKey];
            var productCover = null;
            try {
                productCover = (featuredContentImages[2])[ImageSourceKey];
            } catch (exception) {
                productCover = null
            }
            /* End - Images */

            /* Start - Primary Category */
            var productCategories = jsonObject[CategoriesKey];

            var productCategory = (productCategories[productCategories.length - 1]);

            var productCategoryName = "All Products";
            var productCategoryId = 15;

            productCategories.forEach((productCategory) => {

                var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategoryName = productCategory[NameKey];
                    productCategoryId = productCategory[IdKey];

                }

            });
            /* End - Primary Category */

            /* Start - Attributes */
            var contentAttributes = featuredContentJsonObject[AttributesKey]

            contentAttributes.forEach((attributesJsonObject) => {

                var attributeName = attributesJsonObject[NameKey]
                var attributeValue = AttributeOptionsKey[0].toString()

                //Add More To Document

            });
            /* End - Attributes */

            res.status(200).send('<br/>' + '>   ' + applicationName + ': ' + productIcon + ' | ' + productCategoryName);

        });

    };
    xmlHttpRequest.send();

});

exports.transferGamesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {



});