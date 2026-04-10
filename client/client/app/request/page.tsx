'use client'

import api from '@/app/lib/axios';
import { useState } from 'react';

export default function RequestPage() {

  function getNow() {
    const now = new Date();
    const korNow = now.toLocaleString("ko-KR");
    return korNow;
  }

  // 3초
  function sendFlux() {
    console.log("Flux 방식 실행 10번");
    const start = getNow();
    for(let i=0;i<500;i++) {
      api.post("/api/users", {
        "name": `${i}`,
        "age": 1
      })
          .then(res => console.log(`${getNow()}: ${res.data}`))
          .catch(err => console.log(`${getNow()}: 에러 발생 ${err}`));
    }
    console.log(`시작시간: ${start}`);
  }

  // 약 3~4초걸림
  function sendMvc() {
    console.log("Mvc 방식 실행 10번");
    const start = getNow();
    for(let i=0;i<500;i++) {
      api.post("/users", {
        "name": `${i}`,
        "age": 1
      })
          .then(res => console.log(`${getNow()}: ${res.data}`))
          .catch(err => console.log(`${getNow()}: 에러 발생 ${err}`));
    }
    console.log(`시작시간: ${start}`);
  }

  return (
    <div>
      <button onClick={sendMvc}>mvc 10번 보내기</button>
      <button onClick={sendFlux}>flux 10번 보내기</button>
    </div>
  )
}