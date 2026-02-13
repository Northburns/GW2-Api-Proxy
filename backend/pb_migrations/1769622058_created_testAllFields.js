/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = new Collection({
    "createRule": null,
    "deleteRule": null,
    "fields": [
      {
        "autogeneratePattern": "[a-z0-9]{15}",
        "hidden": false,
        "id": "text3208210256",
        "max": 15,
        "min": 15,
        "name": "id",
        "pattern": "^[a-z0-9]+$",
        "presentable": false,
        "primaryKey": true,
        "required": true,
        "system": true,
        "type": "text"
      },
      {
        "hidden": false,
        "id": "autodate2990389176",
        "name": "created",
        "onCreate": true,
        "onUpdate": false,
        "presentable": false,
        "system": false,
        "type": "autodate"
      },
      {
        "hidden": false,
        "id": "autodate3332085495",
        "name": "updated",
        "onCreate": true,
        "onUpdate": true,
        "presentable": false,
        "system": false,
        "type": "autodate"
      },
      {
        "autogeneratePattern": "",
        "hidden": false,
        "id": "text999008199",
        "max": 10,
        "min": 2,
        "name": "text",
        "pattern": "",
        "presentable": true,
        "primaryKey": false,
        "required": false,
        "system": false,
        "type": "text"
      },
      {
        "convertURLs": true,
        "hidden": false,
        "id": "editor3438408122",
        "maxSize": 400,
        "name": "editor",
        "presentable": false,
        "required": false,
        "system": false,
        "type": "editor"
      },
      {
        "hidden": false,
        "id": "number2526027604",
        "max": 100,
        "min": 0,
        "name": "number",
        "onlyInt": false,
        "presentable": false,
        "required": false,
        "system": false,
        "type": "number"
      },
      {
        "hidden": false,
        "id": "bool1434531474",
        "name": "bool",
        "presentable": false,
        "required": false,
        "system": false,
        "type": "bool"
      },
      {
        "exceptDomains": null,
        "hidden": false,
        "id": "email3885137012",
        "name": "email",
        "onlyDomains": [
          "test.com"
        ],
        "presentable": false,
        "required": false,
        "system": false,
        "type": "email"
      },
      {
        "exceptDomains": [
          "x.com"
        ],
        "hidden": false,
        "id": "url4101391790",
        "name": "url",
        "onlyDomains": null,
        "presentable": false,
        "required": false,
        "system": false,
        "type": "url"
      },
      {
        "hidden": false,
        "id": "date2482226890",
        "max": "",
        "min": "2025-03-20 12:00:00.000Z",
        "name": "datetime",
        "presentable": false,
        "required": false,
        "system": false,
        "type": "date"
      },
      {
        "hidden": false,
        "id": "select1274211008",
        "maxSelect": 1,
        "name": "select",
        "presentable": false,
        "required": false,
        "system": false,
        "type": "select",
        "values": [
          "a",
          "b",
          "c"
        ]
      },
      {
        "hidden": false,
        "id": "file2359244304",
        "maxSelect": 0,
        "maxSize": 5000,
        "mimeTypes": [
          "text/plain"
        ],
        "name": "file",
        "presentable": false,
        "protected": true,
        "required": false,
        "system": false,
        "thumbs": [
          "50x50"
        ],
        "type": "file"
      },
      {
        "cascadeDelete": false,
        "collectionId": "_pb_users_auth_",
        "hidden": false,
        "id": "relation1653163849",
        "maxSelect": 2,
        "minSelect": 1,
        "name": "relation",
        "presentable": true,
        "required": false,
        "system": false,
        "type": "relation"
      },
      {
        "hidden": false,
        "id": "json1795630405",
        "maxSize": 50,
        "name": "json",
        "presentable": false,
        "required": false,
        "system": false,
        "type": "json"
      },
      {
        "hidden": false,
        "id": "geoPoint1587448267",
        "name": "location",
        "presentable": false,
        "required": true,
        "system": false,
        "type": "geoPoint"
      }
    ],
    "id": "pbc_2125374677",
    "indexes": [],
    "listRule": null,
    "name": "testAllFields",
    "system": false,
    "type": "base",
    "updateRule": null,
    "viewRule": null
  });

  return app.save(collection);
}, (app) => {
  const collection = app.findCollectionByNameOrId("pbc_2125374677");

  return app.delete(collection);
})
