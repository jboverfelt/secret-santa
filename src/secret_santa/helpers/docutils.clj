(ns secret-santa.helpers.docutils
  (:require [clj-time.core :as ctime]))

(defn add-created-and-updated [document]
  (assoc document :created_at (ctime/now) :updated_at (ctime/now)))

(defn setup-new-document [document oid]
  (-> document
      (assoc :_id oid)
      (add-created-and-updated)))

(defn touch [document]
  (assoc document :updated_at (ctime/now)))
