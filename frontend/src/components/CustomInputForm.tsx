import { useState } from 'react'
import { useAppSelector } from '@/hooks/useAppSelector'
import { useCustomExtensions } from '@/hooks/useCustomExtensions'

export const CustomInputForm = () => {
  const [input, setInput] = useState('')
  const [error, setError] = useState('')

  const fixedList = useAppSelector((state) => state.fixedExtensions.list ?? [])
  const customList = useAppSelector((state) => state.customExtensions.list ?? [])
  const { addExtension } = useCustomExtensions()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const trimmed = input.trim().toLowerCase()
    if (!trimmed) return

    const isInFixed = fixedList
      .filter((ext): ext is { name: string } => !!ext && typeof ext.name === 'string')
      .some((ext) => ext.name.toLowerCase() === trimmed)

    if (isInFixed) {
      setError('고정 확장자에 이미 존재합니다.')
      return
    }

    const isInCustom = customList
      .filter((ext): ext is { name: string } => !!ext && typeof ext.name === 'string')
      .some((ext) => ext.name === trimmed)

    if (isInCustom) {
      setError('이미 등록된 커스텀 확장자입니다.')
      return
    }

    try {
      await addExtension(trimmed)
      setInput('')
      setError('')
    } catch (err: any) {
      const res = err?.response
      const code = res?.data?.code
      const message = res?.data?.message
      const details = res?.data?.data

      if (res?.status === 409) {
        if (message?.includes('고정 확장자')) {
          setError('고정 확장자에 이미 존재합니다.')
        } else if (message?.includes('커스텀')) {
          setError('이미 등록된 커스텀 확장자입니다.')
        } else {
          setError(message ?? '이미 등록된 확장자입니다.')
        }
      } else if (code === 4002) {
        setError('최대 200개까지 등록할 수 있습니다.')
      } else if (code === 4000 && details) {
        const firstField = Object.keys(details)[0]
        setError(details[firstField] || message || '입력값이 유효하지 않습니다.')
      } else {
        setError(message ?? '등록 중 알 수 없는 오류가 발생했습니다.')
      }
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
