package com.softwaremill.bootzooka.test

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromString}
import com.softwaremill.bootzooka.http.Error_OUT

trait TestSupport:
  extension (v: Either[String, String])
    def shouldDeserializeTo[T: JsonValueCodec]: T = v.map(readFromString[T](_)).right.get
    def shouldDeserializeToError: String = readFromString[Error_OUT](v.left.get).error
