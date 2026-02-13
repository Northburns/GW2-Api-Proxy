/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = app.findCollectionByNameOrId("pbc_1106534921")

  // update collection data
  unmarshal({
    "indexes": [
      "CREATE UNIQUE INDEX `idx_UFr6jQNLFV` ON `test59` (`name`)"
    ],
    "name": "test59"
  }, collection)

  return app.save(collection)
}, (app) => {
  const collection = app.findCollectionByNameOrId("pbc_1106534921")

  // update collection data
  unmarshal({
    "indexes": [
      "CREATE UNIQUE INDEX `idx_UFr6jQNLFV` ON `test1` (`name`)"
    ],
    "name": "test1"
  }, collection)

  return app.save(collection)
})
