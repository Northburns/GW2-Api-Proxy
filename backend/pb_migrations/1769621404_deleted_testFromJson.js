/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = app.findCollectionByNameOrId("pbc_980333658");

  return app.delete(collection);
}, (app) => {
  const collection = new Collection({
    "createRule": "@request.auth.email = \"admin@test.com\"",
    "deleteRule": "@request.auth.email = \"admin@test.com\"",
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
        "autogeneratePattern": "",
        "hidden": false,
        "id": "text3862414132",
        "max": 0,
        "min": 0,
        "name": "field1",
        "pattern": "",
        "presentable": true,
        "primaryKey": false,
        "required": true,
        "system": false,
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
      }
    ],
    "id": "pbc_980333658",
    "indexes": [
      "CREATE UNIQUE INDEX `idx_JIvSlSYnRn` ON `fromJson` (`field1`)"
    ],
    "listRule": "@request.auth.email = \"admin@test.com\"",
    "name": "testFromJson",
    "system": false,
    "type": "base",
    "updateRule": "@request.auth.email = \"admin@test.com\"",
    "viewRule": "@request.auth.email = \"admin@test.com\""
  });

  return app.save(collection);
})
