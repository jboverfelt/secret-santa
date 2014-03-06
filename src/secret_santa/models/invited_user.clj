(ns secret-santa.models.invited-user
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [secret-santa.helpers.dbutils :as db]
            [secret-santa.helpers.docutils :as doc])
  (:import [org.bson.types ObjectId]))

(defn find-invited-user-by-email [email]
  (db/setup-db)
  (mc/find-one-as-map "invites" {:email email}))

(defn create-invited-user [email]
  (let [oid (ObjectId.)]
    (db/setup-db)
    (mc/insert-and-return "invites" (doc/setup-new-document {:email email} oid))))

(defn remove-invited-user [email]
  (db/setup-db)
  (mc/remove "invites" {:email email}))
