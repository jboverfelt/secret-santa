(ns secret-santa.helpers.dbutils
  (:require [monger.core :as mg]))

(defn setup-db []
  (let [uri (get (System/getenv) "MONGOHQ_URI" "mongodb://127.0.0.1/santa")]
    (when-not (bound? (var mg/*mongodb-connection*))
      (mg/connect-via-uri! uri))
    (mg/set-db! (mg/get-db "santa"))))
