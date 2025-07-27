import { useState } from 'react'
import { useAppDispatch } from '../hooks/useAppDispatch'
import { createCustomExtension } from '../store/customExtension'

export const CustomInputForm = () => {
  const dispatch = useAppDispatch()
  const [input, setInput] = useState('')

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const trimmed = input.trim()
    if (!trimmed) return
    dispatch(createCustomExtension(trimmed))
    setInput('')
  }

  return (
    <form onSubmit={handleSubmit} className="flex gap-2 mt-4">
      <input
        value={input}
        onChange={e => setInput(e.target.value)}
        placeholder="확장자 입력 (예: sh)"
        maxLength={20}
        className="border px-2 py-1 rounded w-48"
      />
      <button type="submit" className="bg-blue-500 text-white px-3 py-1 rounded">
        추가
      </button>
    </form>
  )
}
