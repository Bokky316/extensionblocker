import React from 'react'
import { FixedList } from './components/FixedList'
import { CustomInputForm } from './components/CustomInputForm'
import { CustomList } from './components/CustomList'

function App() {
  return (
    <div className="max-w-xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-6 text-center">확장자 차단 시스템</h1>

      <section className="mb-6">
        <FixedList />
      </section>

      <section className="mb-6">
        <CustomInputForm />
      </section>

      <section>
        <CustomList />
      </section>
    </div>
  )
}

export default App
