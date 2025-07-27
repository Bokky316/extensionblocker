import React from 'react'
import { FixedList } from './components/FixedList'
import { CustomInputForm } from './components/CustomInputForm'
import { CustomList } from './components/CustomList'

function App() {
  return (
    <div className="max-w-layout mx-auto font-sans text-body text-grayText">
      <h1 className="text-heading text-center text-black font-bold mb-section">
        확장자 차단 시스템
      </h1>

      <section className="mb-section">
        <FixedList />
      </section>

      <section className="mb-section">
        <CustomInputForm />
      </section>

      <section>
        <CustomList />
      </section>
    </div>
  )
}

export default App
