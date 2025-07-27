import { useState } from 'react'
import { useAppSelector } from '@/hooks/useAppSelector'
import { useCustomExtensions } from '@/hooks/useCustomExtensions'

export const CustomInputForm = () => {
  const [input, setInput] = useState('')
  const [error, setError] = useState('')

  const fixedList = useAppSelector((state) => state.fixedExtensions.list)
  const customList = useAppSelector((state) => state.customExtensions.list)
  const { addExtension } = useCustomExtensions()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const trimmed = input.trim().toLowerCase()
    if (!trimmed) return

    const isInFixed = fixedList.some((ext) => ext.name.toLowerCase() === trimmed)
    if (isInFixed) {
      setError('고정 확장자에 이미 존재합니다.')
      return
    }

    const isInCustom = customList.some((ext) => ext.name.toLowerCase() === trimmed)
    if (isInCustom) {
      setError('이미 등록된 커스텀 확장자입니다.')
      return
    }

    try {
      await addExtension(trimmed)
      setInput('')
      setError('')
    } catch {
      setError('등록 중 오류가 발생했습니다.')
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-2 mt-4 w-60">
      <div className="flex gap-2">
        <input
          value={input}
          onChange={(e) => {
            setInput(e.target.value)
            setError('')
          }}
          placeholder="확장자 입력 (예: conf)"
          maxLength={20}
          className="border px-2 py-1 rounded w-full"
        />
        <button type="submit" className="bg-blue-500 text-white px-3 py-1 rounded">
          추가
        </button>
      </div>
      {error && <p className="text-red-500 text-sm">{error}</p>}
    </form>
  )
}
